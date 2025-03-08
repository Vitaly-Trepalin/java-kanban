package main.task_tracker;

import main.task_tracker.task.Epic;
import main.task_tracker.task.Status;
import main.task_tracker.task.Subtask;
import main.task_tracker.task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager;

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
            epic.setStatus();
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
    public void addNewTask(Task task, Status status) {
        task.setStatus(status); //установка статуса задачи
        task.setId(increaseId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addNewEpic(Epic epic) {
        epic.setId(increaseId());
        epic.setStatus(); //установка статуса эпика
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addNewSubtask(Subtask subtask, Status status) {
        subtask.setId(increaseId());
        subtask.setStatus(status); //установка статуса подзадачи

        Epic epic = subtask.getEpic();
        if (epics.containsValue(epic)) { //существуют ли эпик этой подзадачи
            List<Subtask> subtaskList = epic.getSubtasks();
            subtaskList.add(subtask); //добавление в эпик данных о новой подзадаче
            epic.setSubtasks(subtaskList);

            epic.setStatus(); //актуализация статуса эпика
        } else {
            System.out.println("Эпика с такой подзадачей нет");
            return;
        }

        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void taskUpdate(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задачи с id = " + task.getId() + " в списке задач нет. Обновление невозможно");
        }

    }

    @Override
    public void epicUpdate(Epic epic) {
        if (epics.containsKey(epic.getId()) &&
                epic.getSubtasks().equals(epics.get(epic.getId()).getSubtasks())) {
            epics.put(epic.getId(), epic);

        } else {
            System.out.println("Эпика с id = " + epic.getId() + " в списке задач нет. Обновление невозможно");
        }

    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        Epic epic = subtask.getEpic(); //блок кода актуализации статуса эпика, в который входит подзадача
        List<Subtask> listOfSubtasksOfTheEpic = epic.getSubtasks();

        //проверка наличия в списке подзадач эпика, подзадачи c нужным id
        boolean isPresenceOfSubtaskWithThisNumber = false;
        int subtaskNumberInTheSubtaskList = 0;
        for (Subtask subtask1 : listOfSubtasksOfTheEpic) {
            if (subtask1.getId() == subtask.getId()) {
                isPresenceOfSubtaskWithThisNumber = true;
                break;
            }
            subtaskNumberInTheSubtaskList++;
        }

        if (isPresenceOfSubtaskWithThisNumber) { //внесение изменений во все, связанные с обновлением подзадачи, поля
            listOfSubtasksOfTheEpic.remove(subtaskNumberInTheSubtaskList);
            listOfSubtasksOfTheEpic.add(subtask);
            epic.setSubtasks(listOfSubtasksOfTheEpic);
            epic.setStatus();
            epics.put(epic.getId(), epic);
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Подзадача ссылается на эпик, в списке подзадач которого, нет подзадачи с id = " +
                    subtask.getId() + ". Обновление данной подзадачи невозможно.");
        }
    }

    @Override
    public void deleteByIdTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdEpic(int id) {
        for (Subtask subtask : epics.get(id).getSubtasks()) { //блок удаления подзадач удаляемого эпика
            historyManager.remove(subtask.getId());
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdSubtask(int id) {
        Epic epic = subtasks.get(id).getEpic(); //получение объекта эпика из подзадачи
        List<Subtask> subtaskList = epic.getSubtasks();
        subtaskList.remove(subtasks.get(id));
        epic.setStatus();

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

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Map<Integer, Subtask> getSubtasks() {
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
