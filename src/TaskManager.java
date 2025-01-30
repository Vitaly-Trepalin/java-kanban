import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();


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
        task.setId(increaseId());
        tasks.put(task.getId(), task);
    }

    public void creationEpic(Epic epic) {
        epic.setId(increaseId());
        epic.setStatus(); //установка статуса эпика
        epics.put(epic.getId(), epic);
    }

    public void creationSubtask(Subtask subtask, Status status) {
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

    public void taskUpdate(Task task) {
        tasks.put(task.getId(), task);
    }

    public void epicUpdate(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void subtaskUpdate(Subtask subtask) {
        Epic epic = subtask.getEpic(); //блок кода актуализации статуса эпика, в который входит подзадача
        epic.setStatus();
        epics.put(epic.getId(), epic);

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
        Epic epic = subtasks.get(id).getEpic(); //получение объекта эпика из подзадачи
        List<Subtask> subtaskList = epic.getSubtasks();
        subtaskList.remove(subtasks.get(id));
        epic.setStatus();

        subtasks.remove(id);

        epics.put(epic.getId(), epic);
    }

    public List<Subtask> gettingAllSubtasks(Epic epic) {
        return new ArrayList<>(epic.getSubtasks());
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
}
