package project;

import project.exception.AdditionAndUpdateException;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;

import java.time.ZoneOffset;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>(); // данные поля не финальные, так как
    // переприсваиваются в методах deletingTasks, deletingEpics, deletingSubtask
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager;


    public Set<Task> getPrioritizedTasks() {
        TreeSet<Task> sortedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
        sortedTasks.addAll(tasks.values().stream()
                .filter(task -> task.getStartTime() != null)
                .toList());
        sortedTasks.addAll(subtasks.values().stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .toList());
        return sortedTasks;
    }

    public boolean hasIntersections(Task task) {
        if (task.getStartTime() == null) {
            return false;
        }
        return getPrioritizedTasks().stream() //проверка пересечения задач и подзадач по времени методом
                // наложения отрезков
                .filter(task1 -> task.getId() != task1.getId())
                .anyMatch(task1 -> {
                    long startTime1 = task.getStartTime().toEpochSecond(ZoneOffset.ofTotalSeconds(0));
                    long startTime2 = task1.getStartTime().toEpochSecond(ZoneOffset.ofTotalSeconds(0));

                    long endTime1 = task.getStartTime()
                            .plus(task.getDuration()).toEpochSecond(ZoneOffset.ofTotalSeconds(0));
                    long endTime2 = task1.getStartTime()
                            .plus(task.getDuration()).toEpochSecond(ZoneOffset.ofTotalSeconds(0));

                    return Math.max(startTime1, startTime2) <= Math.min(endTime1, endTime2);
                });
    }

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> gettingListOfAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> gettingListOfAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> gettingListOfAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deletingTasks() {
        tasks = new HashMap<>();
    }

    @Override
    public void deletingEpics() {
        epics = new HashMap<>();
        subtasks = new HashMap<>(); //если я удаляю все эпики, то автоматически удаляются и все подзадачи
    }

    @Override
    public void deletingSubtask() {
        subtasks = new HashMap<>();
        for (Integer key : epics.keySet()) { //удаление всех подзадач приводит к изменению статуса всех эпиков на NEW
            // и удаление всех подзадач в эпике
            Epic epic = epics.get(key);
            epic.setSubtasks(new ArrayList<>());
            updateStatusDurationStartTimeEndTimeOfTheEpic(epic);
            epic.setStatus(epic.setStatusForEpic());
            epics.put(key, epic);
        }
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void addNewTask(Task task) {
        task.setId(increaseId());
        if (hasIntersections(task)) {
            id--;
            throw new AdditionAndUpdateException("Время выполнения задачи пересекается с уже существующими задачами. " +
                    "Задача " + task.getNameTask() + "\" не добавлена");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void addNewEpic(Epic epic) {
        epic.setStatus(epic.setStatusForEpic());
        epic.setId(increaseId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        subtask.setId(increaseId());
        if (hasIntersections(subtask)) {
            id--;
            throw new AdditionAndUpdateException("Время выполнения задачи пересекается с уже существующими задачами. " +
                    "Задача " + subtask.getNameTask() + "\" не добавлена");
        }

        int epicId = subtask.getEpicId();
        if (epics.containsKey(epicId)) { //существуют ли эпик этой подзадачи
            Epic epic = epics.get(epicId);
            List<Subtask> subtaskList = epic.getSubtasks();
            subtaskList.add(subtask); //добавление в эпик данных о новой подзадаче
            epic.setSubtasks(subtaskList);
            updateStatusDurationStartTimeEndTimeOfTheEpic(epic);
        } else {
            id--;
            throw new AdditionAndUpdateException("Эпика с такой подзадачей нет. Epic \"" +
                    subtask.getNameTask() + "\" не добавлена");
        }
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void taskUpdate(Task task) {
        if (hasIntersections(task)) {
            throw new AdditionAndUpdateException("Время выполнения задачи пересекается с уже существующими задачами. " +
                    "Задача " + task.getNameTask() + " не добавлена");
        }
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            throw new AdditionAndUpdateException("Задачи с id=\"" +
                    task.getId() + "\" в списке задач нет. Обновление невозможно");
        }
    }

    @Override
    public void epicUpdate(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            throw new AdditionAndUpdateException("Эпика с id = " + epic.getId() + " в списке задач нет. " +
                    "Обновление невозможно");
        }

    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            throw new AdditionAndUpdateException("Подзадача ссылается на несуществующий эпик. Эпика с id=" +
                    subtask.getEpicId() + " нет");
        }
        if (hasIntersections(subtask)) {
            throw new AdditionAndUpdateException("Время выполнения задачи пересекается с уже существующими задачами. " +
                    "Задача " + subtask.getNameTask() + " не добавлена");
        }
        Epic epic = epics.get(subtask.getEpicId()); //блок кода актуализации статуса эпика, в который входит подзадача
        List<Subtask> listOfSubtasksOfTheEpic = epic.getSubtasks();

        //проверка наличия в списке подзадач эпика, подзадачи c нужным id
        boolean isPresenceOfSubtaskWithThisNumber = false;
        int subtaskNumberInTheSubtaskList = 0;
        for (int i = 0; i < listOfSubtasksOfTheEpic.size(); i++) {
            if (listOfSubtasksOfTheEpic.get(i).equals(subtask)) {
                isPresenceOfSubtaskWithThisNumber = true;
                subtaskNumberInTheSubtaskList = i;
                break;
            }
        }

        if (isPresenceOfSubtaskWithThisNumber) { //внесение изменений во все, связанные с обновлением подзадачи, поля
            listOfSubtasksOfTheEpic.remove(subtaskNumberInTheSubtaskList);
            listOfSubtasksOfTheEpic.add(subtask);
            epic.setSubtasks(listOfSubtasksOfTheEpic);
            updateStatusDurationStartTimeEndTimeOfTheEpic(epic);
            epics.put(epic.getId(), epic);
            subtasks.put(subtask.getId(), subtask);
        } else {
            throw new AdditionAndUpdateException("Подзадача ссылается на эпик, в списке подзадач которого, " +
                    "нет подзадачи с id=" + subtask.getId() + ". Обновление данной подзадачи невозможно.");
        }
    }

    @Override
    public void deleteByIdTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdEpic(int id) {
        List<Subtask> subtasksOfTheEpicBeingDeleted = epics.get(id).getSubtasks();
        for (Subtask subtask : subtasksOfTheEpicBeingDeleted) { //блок удаления подзадач из истории
            // объекта project.HistoryManager и удаления подзадач из коллекции subtasks объекта
            // project.InMemoryTaskManager
            historyManager.remove(subtask.getId());
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdSubtask(int id) {
        Epic epic = epics.get(subtasks.get(id).getEpicId()); //получение объекта эпика из подзадачи
        List<Subtask> subtaskList = epic.getSubtasks();
        subtaskList.remove(subtasks.get(id));
        updateStatusDurationStartTimeEndTimeOfTheEpic(epic);
        subtasks.remove(id);
        epics.put(epic.getId(), epic);
        historyManager.remove(id);
    }

    public List<Subtask> gettingAllSubtasks(Epic epic) {
        return new ArrayList<>(epic.getSubtasks());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int increaseId() {
        return id++;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public void updateStatusDurationStartTimeEndTimeOfTheEpic(Epic epic) {
        epic.setStatus(epic.setStatusForEpic());  //актуализация статуса эпика
        epic.setDuration(epic.setDurationForEpics()); //актуализация продолжительности всех подзадач эпика
        epic.setStartTime(epic.setStartTimeForEpics()); //актуализация старта самой ранней подзадачи эпика
        epic.setEndTime(epic.setEndTimeForEpics()); //актуализация окончания самой поздней подзадачи эпика
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return id == that.id && Objects.equals(tasks, that.tasks) && Objects.equals(epics, that.epics) &&
                Objects.equals(subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tasks, epics, subtasks);
    }


}
