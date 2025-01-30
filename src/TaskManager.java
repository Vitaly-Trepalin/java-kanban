import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;

    public List<Task> gettingListOfAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> gettingListOfAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> gettingListOfAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    public void deletingTasks() {
        tasks = new HashMap<>();
    }

    public void deletingEpic() {
        epics = new HashMap<>();
        subtasks = new HashMap<>(); //если я удаляю все эпики, то автоматически удаляются и все подзадачи
    }

    public void deletingSubtask() {
        subtasks = new HashMap<>();
        for (Integer key : epics.keySet()) { //удаление всех подзадач приводит к изменению статуса всех эпиков на NEW
            Epic epic = epics.get(key);
            epic.setStatus();
            epics.put(key, epic);
        }
    }

    public Task getByIdTask(int id) {
        return tasks.get(id);
    }

    public Epic getByIdEpics(int id) {
        return epics.get(id);
    }

    public Subtask getByIdSubtask(int id) {
        return subtasks.get(id);
    }

    public void creationTask(Task task, Status status) {
        task.setStatus(status); //установка статуса задачи
        tasks.put(increaseId(), task);
    }

    public void creationEpic(Epic epic) {
        epic.setStatus(); //установка статуса эпика
        epics.put(increaseId(), epic);
    }

    public void creationSubtask(Subtask subtask, Status status) {
        subtask.setStatus(status); //установка статуса подзадачи
        if (epics.containsKey(subtask.getId())) { //проверка на предмет входит ли эта подзадача в существующие эпики
            Epic epic = epics.get(subtask.getId()); //блок кода добавляющий подзадачу в список подзадач эпика
            List<Subtask> subtasks = epic.getSubtasks();
            subtasks.add(subtask);
            epic.setSubtasks(subtasks);

            epic.setStatus(); //после изменения списка подзадач, актуализация статуса эпика, в который входи подзадача

            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Нет эпика с таким id");
            return; //не может быть подзадачи вне эпика, метод завершается
        }
        subtasks.put(increaseId(), subtask);
    }

    public void taskUpdate(Task task) {
        tasks.put(task.getId(), task);
    }

    public void epicUpdate(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void subtaskUpdate(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId()); //блок кода актуализации статуса эпика, в который входит подзадача
        epic.setStatus();
        epics.put(subtask.getEpicId(), epic);

        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteByIdTask(int id) {
        tasks.remove(id);
    }

    public void deleteByIdEpic(int id) {
        for (Subtask subtask : epics.get(id).getSubtasks()) { //блок удаления подзадач удаляемого эпика
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
    }

    public void deleteByIdSubtask(int id) {
        int epicId = subtasks.get(id).getEpicId(); //получение id эпика для актуализации статуса

        subtasks.remove(id);

        Epic epic = epics.get(epicId); //блок актуализации статуса эпика после удаления подзадачи
        epic.setStatus();
        epics.put(epicId, epic);
    }

    public List<Subtask> gettingAllSubtasks(Epic epic) {
        return new ArrayList<>(epic.getSubtasks());
    }

    public int increaseId() {
        return id++;
    }
}
