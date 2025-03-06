package test;

import main.HistoryManager;
import main.InMemoryHistoryManager;
import main.InMemoryTaskManager;
import main.task.Epic;
import main.task.Status;
import main.task.Subtask;
import main.task.Task;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InMemoryHistoryManagerTest {

    @Test
    @Order(1)
    void checkGettingTasksFromHistory() { //проверка получения всей истории(метод getHistory())
        List<Task> expectedTasks = addingTasksToTheExpectedList();

        List<Task> resultOfTheMethods = addTasksInMemoryHistoryManager().getHistory();

        Assertions.assertEquals(expectedTasks, resultOfTheMethods, "некорректно работает " +
                "получение всех задач из истории");
    }

    @Test
    @Order(2)
    void checkForDeletionFromHistory() { //проверка удаления эпика 2 с входящими в него подзадачами 1, 2 из истории
        List<Task> expectedTaskListInHistory = addingTasksToTheExpectedList();
        expectedTaskListInHistory.remove(6);
        expectedTaskListInHistory.remove(5);
        expectedTaskListInHistory.remove(3);

        HistoryManager historyManager = addTasksInMemoryHistoryManager();
        historyManager.remove(3);
        historyManager.remove(5);
        historyManager.remove(6);

        List<Task> resultOfTheMethods = historyManager.getHistory();

        Assertions.assertEquals(expectedTaskListInHistory, resultOfTheMethods, "некорректно работает " +
                "удаление задач из истории");
    }

    @Test
    @Order(3)
    void checkAddTask() { //проверка добавления
        HistoryManager historyManager = addTasksInMemoryHistoryManager();
        Task expectedTask = new Task("Четвёртая задача", "Описание четвёртой задачи", 8 ,
                Status.NEW);

        historyManager.add(expectedTask);
        Task resultOfTheMethods = historyManager.getHistory().getLast();

        Assertions.assertEquals(expectedTask, resultOfTheMethods, "некорректно работает добавление задачи");
    }

    public InMemoryHistoryManager addTasksInMemoryHistoryManager() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        //создание 3 задач
        Task task1 = new Task("Первая задача", "Описание первой задачи");
        Task task2 = new Task("Вторая задача", "Описание второй задачи");
        Task task3 = new Task("Третья задача", "Описание третьей задачи");
        inMemoryTaskManager.addNewTask(task1, Status.NEW);
        inMemoryTaskManager.addNewTask(task2, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task3, Status.DONE);

        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);

        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи", epic1);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание второй подзадачи", epic1);
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание третьей подзадачи", epic2);
        inMemoryTaskManager.addNewSubtask(subtask1, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewSubtask(subtask2, Status.DONE);
        inMemoryTaskManager.addNewSubtask(subtask3, Status.NEW);

        //добавление в InMemoryHistoryManager
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(epic1);
        historyManager.add(epic2);
        historyManager.add(subtask1);
        historyManager.add(subtask2);
        historyManager.add(subtask3);

        return historyManager;
    }

    public List<Task> addingTasksToTheExpectedList() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        List<Task> expectedTaskListInHistory = new ArrayList<>();

        //создание 3 задач
        Task task1 = new Task("Первая задача", "Описание первой задачи");
        Task task2 = new Task("Вторая задача", "Описание второй задачи");
        Task task3 = new Task("Третья задача", "Описание третьей задачи");
        inMemoryTaskManager.addNewTask(task1, Status.NEW);
        inMemoryTaskManager.addNewTask(task2, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task3, Status.DONE);

        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);

        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи", epic1);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание второй подзадачи", epic1);
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание третьей подзадачи", epic2);
        inMemoryTaskManager.addNewSubtask(subtask1, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewSubtask(subtask2, Status.DONE);
        inMemoryTaskManager.addNewSubtask(subtask3, Status.NEW);

        //добавление задач, эпиков, подзадач в ожидаемый список
        expectedTaskListInHistory.add(task1);
        expectedTaskListInHistory.add(task2);
        expectedTaskListInHistory.add(task3);
        expectedTaskListInHistory.add(epic1);
        expectedTaskListInHistory.add(epic2);
        expectedTaskListInHistory.add(subtask1);
        expectedTaskListInHistory.add(subtask2);
        expectedTaskListInHistory.add(subtask3);

        return expectedTaskListInHistory;
    }
}