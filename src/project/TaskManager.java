package project;

import project.task.Epic;
import project.task.Subtask;
import project.task.Task;

import java.util.List;
import java.util.Set;

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

    void addNewTask(Task task);

    void addNewEpic(Epic epic);

    void addNewSubtask(Subtask subtask);

    void taskUpdate(Task task);

    void epicUpdate(Epic epic);

    void subtaskUpdate(Subtask subtask);

    void deleteByIdTask(int id);

    void deleteByIdEpic(int id);

    void deleteByIdSubtask(int id);

    HistoryManager getHistoryManager();

    Set<Task> getPrioritizedTasks();
}
