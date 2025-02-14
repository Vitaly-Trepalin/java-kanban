package test;

import main.InMemoryTaskManager;
import main.task.Epic;
import main.task.Status;
import main.task.Subtask;
import main.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    public static InMemoryTaskManager creatingAndFillingClassInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Первая задача", "123");
        Task task2 = new Task("Вторая задача", "456");
        taskManager.addNewTask(task1, Status.NEW);
        taskManager.addNewTask(task2, Status.NEW);
        Epic epic = new Epic("Второй эпик", "Описание второго эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", epic);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", epic);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", epic);
        taskManager.addNewSubtask(subtask1, Status.NEW);
        taskManager.addNewSubtask(subtask2, Status.NEW);
        taskManager.addNewSubtask(subtask3, Status.NEW);
        return taskManager;
    }

    @Test
    void checkDeletingTasks() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Map<Integer, Task> expectedHashMap = new HashMap<>();

        taskManager.deletingTasks();
        Map<Integer, Task> resultOfTheMethod = taskManager.getTasks();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod),"хеш-таблица c задачами не отчистилась");
    }

    @Test
    void checkDeletingEpic() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        HashMap<Integer, Epic> expectedHashMap = new HashMap<>();

        taskManager.deletingEpics();
        Map<Integer, Epic> resultOfTheMethod = taskManager.getEpics();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod),"хеш-таблица с эпиками не отчистилась");
    }

    @Test
    void checkDeletingSubtask() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        HashMap<Integer, Subtask> expectedHashMap = new HashMap<>();

        taskManager.deletingSubtask();
        Map<Integer, Subtask> resultOfTheMethod = taskManager.getSubtasks();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod),"хеш-таблица с подзадачами не " +
                "отчистилась");
    }


    @Test
    void checkAddNewTaskAndGettingTaskByNumber() {
        Task expectedTask = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW);
        Task task1 = new Task("Первая задача", "Описание первой задачи");

        inMemoryTaskManager.addNewTask(task1, Status.NEW);
        Task resultOfTheMethod = inMemoryTaskManager.getTask(0);

        Assertions.assertTrue(expectedTask.equals(resultOfTheMethod), "задача была некорректно создана или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewEpicAndGettingEpicByNumber() {
        Epic expectedEpic = new Epic("Второй эпик", "Описание второго эпика", 0, Status.NEW,
                new ArrayList<>());
        Epic epic = new Epic("Второй эпик", "Описание второго эпика");

        inMemoryTaskManager.addNewEpic(epic);
        Epic resultOfTheMethod = inMemoryTaskManager.getEpic(0);

        Assertions.assertTrue(expectedEpic.equals(resultOfTheMethod), "эпик был некорректно создан или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewSubtaskAndGettingSubtaskByNumber() {
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", 0, Status.NEW,
                new ArrayList<>());
        Subtask expectedSubtask = new Subtask("Первая подзадача", "Описание первой подзадачи", 1,
                Status.NEW, epic);

        inMemoryTaskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Первая подзадача", "Описание первой подзадачи", epic);

        inMemoryTaskManager.addNewSubtask(subtask, Status.NEW);
        Subtask resultOfTheMethod = inMemoryTaskManager.getSubtask(1);

        Assertions.assertTrue(expectedSubtask.equals(resultOfTheMethod), "подзадача была некорректно создана " +
                "или некорректно работает получение по Id");
    }

    @Test
    void checkTaskUpdate() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Map<Integer, Task> mapTasks = taskManager.getTasks();
        Integer keyOfTheItemToUpdate = 0;
        for (Integer key : mapTasks.keySet()) {
            keyOfTheItemToUpdate = key;
            break;
        }
        Task expectedTask = mapTasks.get(keyOfTheItemToUpdate);
        expectedTask.setNameTask("Обновленная задача");

        taskManager.taskUpdate(expectedTask);
        Task resultOfTheMethod = taskManager.getTask(keyOfTheItemToUpdate);

        Assertions.assertTrue(expectedTask.equals(resultOfTheMethod), "Задача некорректно обновилась");
    }

    @Test
    void checkEpicUpdate() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Map<Integer, Epic> mapEpics = taskManager.getEpics();
        Integer keyOfTheItemToUpdate = 0;
        for (Integer key : mapEpics.keySet()) {
            keyOfTheItemToUpdate = key;
            break;
        }
        Epic expectedEpic = mapEpics.get(keyOfTheItemToUpdate);
        expectedEpic.setNameTask("Обновлённый эпик");

        taskManager.epicUpdate(expectedEpic);
        Epic resultOfTheMethod = taskManager.getEpic(keyOfTheItemToUpdate);

        Assertions.assertTrue(expectedEpic.equals(resultOfTheMethod), "Эпик некорректно обновился");
    }

    @Test
    void checkSubtaskUpdate() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Map<Integer, Subtask> mapSubtasks = taskManager.getSubtasks();
        Integer keyOfTheItemToUpdate = 0;
        for (Integer key : mapSubtasks.keySet()) {
            keyOfTheItemToUpdate = key;
            break;
        }
        Subtask expectedSubtask = mapSubtasks.get(keyOfTheItemToUpdate);
        expectedSubtask.setNameTask("Обновленная подзадача");

        taskManager.subtaskUpdate(expectedSubtask);
        Subtask resultOfTheMethod = taskManager.getSubtask(keyOfTheItemToUpdate);

        Assertions.assertTrue(expectedSubtask.equals(resultOfTheMethod), "Задача некорректно обновилась");
    }

}