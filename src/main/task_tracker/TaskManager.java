package main.task_tracker;

import main.task_tracker.task.Epic;
import main.task_tracker.task.Status;
import main.task_tracker.task.Subtask;
import main.task_tracker.task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> gettingListOfAllTasks();

    List<Epic> gettingListOfAllEpic();

    List<Subtask> gettingListOfAllSubtask();

    void deletingTasks();

    void deletingEpics();

    void deletingSubtask();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void addNewTask(Task task, Status status);

    void addNewEpic(Epic epic);

    void addNewSubtask(Subtask subtask, Status status);

    void taskUpdate(Task task);

    void epicUpdate(Epic epic);

    void subtaskUpdate(Subtask subtask);

    void deleteByIdTask(int id);

    void deleteByIdEpic(int id);

    void deleteByIdSubtask(int id);
}
