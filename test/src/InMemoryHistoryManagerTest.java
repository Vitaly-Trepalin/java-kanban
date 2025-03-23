import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest {

    public List<Task> creatingAndFillingTheTaskManager() {
        List<Task> expectedTaskListInHistory = new ArrayList<>();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        //создание 3 задач
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS);
        Task task3 = new Task("Третья задача", "Описание третьей задачи", Status.DONE);
        inMemoryTaskManager.addNewTask(task1);
        inMemoryTaskManager.addNewTask(task2);
        inMemoryTaskManager.addNewTask(task3);
        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика", Status.NEW);
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);
        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);
        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.NEW, 3);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", Status.IN_PROGRESS, 3);
        Subtask subtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", Status.DONE, 4);
        inMemoryTaskManager.addNewSubtask(subtask1);
        inMemoryTaskManager.addNewSubtask(subtask2);
        inMemoryTaskManager.addNewSubtask(subtask3);

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

    public InMemoryHistoryManager creatingAndFillingTheHistoryManager() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        //создание 3 задач
        Task task1 = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", 1, Status.IN_PROGRESS);
        Task task3 = new Task("Третья задача", "Описание третьей задачи", 2, Status.DONE);
        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика", 3, Status.NEW);
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика", 4, Status.NEW);
        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 5, Status.NEW, 3);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", 6, Status.IN_PROGRESS, 3);
        Subtask subtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", 7, Status.DONE, 4);
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

    @Test
    void checkGettingTasksFromHistory() { //проверка получения всей истории(метод getHistory())
        List<Task> expectedTaskListInHistory = creatingAndFillingTheTaskManager();

        List<Task> resultOfTheMethods = creatingAndFillingTheHistoryManager().getHistory();

        Assertions.assertEquals(expectedTaskListInHistory, resultOfTheMethods, "некорректно работает " +
                "получение всех задач из истории");
    }

    @Test
    void checkForDeletionFromHistory() { //проверка удаления эпика 2 с входящими в него подзадачами 1, 2 из истории
        List<Task> expectedTaskListInHistory = creatingAndFillingTheTaskManager();
        expectedTaskListInHistory.remove(6);
        expectedTaskListInHistory.remove(5);
        expectedTaskListInHistory.remove(3);

        List<Task> resultOfTheMethods = creatingAndFillingTheHistoryManager().getHistory();
        resultOfTheMethods.remove(6);
        resultOfTheMethods.remove(5);
        resultOfTheMethods.remove(3);

        Assertions.assertEquals(expectedTaskListInHistory, resultOfTheMethods, "некорректно работает " +
                "удаление задач из истории");
    }

    @Test
    void checkAddTask() { //проверка добавления
        Task expectedTask = new Task("Четвёртая задача", "Описание четвёртой задачи", 8,
                Status.NEW);

        InMemoryHistoryManager historyManager = creatingAndFillingTheHistoryManager();
        historyManager.add(expectedTask);
        Task resultOfTheMethods = historyManager.getHistory().getLast();

        Assertions.assertEquals(expectedTask, resultOfTheMethods, "некорректно работает добавление задачи");
    }
}