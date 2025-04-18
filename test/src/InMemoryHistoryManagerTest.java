import project.InMemoryHistoryManager;
import project.InMemoryTaskManager;
import project.task.Epic;
import project.task.Status;
import project.task.Subtask;
import project.task.Task;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest {

    public List<Task> creatingAndFillingTheTaskManager() {
        List<Task> expectedTaskListInHistory = new ArrayList<>();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        //создание 3 задач
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 15,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                30, LocalDateTime.of(2025, 4, 4, 7, 0));
        Task task3 = new Task("Третья задача", "Описание третьей задачи", Status.DONE,
                45, LocalDateTime.of(2025, 4, 4, 8, 0));
        inMemoryTaskManager.addNewTask(task1);
        inMemoryTaskManager.addNewTask(task2);
        inMemoryTaskManager.addNewTask(task3);
        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);
        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 15, LocalDateTime.of(2025, 4, 4, 9, 0),
                3);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание второй подзадачи",
                Status.IN_PROGRESS, 30, LocalDateTime.of(2025, 4, 4, 10,
                0),
                3);
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание третьей подзадачи",
                Status.DONE, 45, LocalDateTime.of(2025, 4, 4, 11, 0),
                4);
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
        Task task1 = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW, 15,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", 1, Status.IN_PROGRESS,
                30, LocalDateTime.of(2025, 4, 4, 7, 0));
        Task task3 = new Task("Третья задача", "Описание третьей задачи", 2, Status.DONE,
                45, LocalDateTime.of(2025, 4, 4, 8, 0));

        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика", 3, Status.IN_PROGRESS,
                45, LocalDateTime.parse("2025-04-04T09:00"));
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика", 4, Status.DONE,
                45, LocalDateTime.parse("2025-04-04T11:00"));

        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 5, Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 9, 0), 3);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", 6, Status.IN_PROGRESS, 30,
                LocalDateTime.of(2025, 4, 4, 10, 0), 3);
        Subtask subtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", 7, Status.DONE, 45,
                LocalDateTime.of(2025, 4, 4, 11, 0), 4);
        //добавление в project.InMemoryHistoryManager
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
    void checkForDeletionFromHistory() { //проверка удаления задач и подзадач из начала, середины и конца
        List<Task> expectedTaskListInHistory = creatingAndFillingTheTaskManager();
        expectedTaskListInHistory.remove(7);
        expectedTaskListInHistory.remove(5);
        expectedTaskListInHistory.remove(0);

        List<Task> resultOfTheMethods = creatingAndFillingTheHistoryManager().getHistory();
        resultOfTheMethods.remove(7);
        resultOfTheMethods.remove(5);
        resultOfTheMethods.remove(0);

        Assertions.assertEquals(expectedTaskListInHistory, resultOfTheMethods, "некорректно работает " +
                "удаление задач из истории");
    }

    @Test
    void checkAddTask() { //проверка добавления
        Task expectedTask = new Task("Четвёртая задача", "Описание четвёртой задачи", 8,
                Status.NEW, 45, LocalDateTime.of(2025, 4, 4, 12, 0));

        InMemoryHistoryManager historyManager = creatingAndFillingTheHistoryManager();
        historyManager.add(expectedTask);
        Task resultOfTheMethods = historyManager.getHistory().getLast();

        Assertions.assertEquals(expectedTask, resultOfTheMethods, "некорректно работает добавление задачи");
    }

    @Test
    void checkAddDuplicateTask() { //проверка на отсутствие дублирования при добавлении задач в историю
        Task task = new Task("Четвёртая задача", "Описание четвёртой задачи", 8,
                Status.NEW, 45, LocalDateTime.of(2025, 4, 4, 12, 0));

        InMemoryHistoryManager historyManager1 = creatingAndFillingTheHistoryManager();
        historyManager1.add(task);
        historyManager1.add(task);
        historyManager1.add(task);

        InMemoryHistoryManager historyManager2 = creatingAndFillingTheHistoryManager();
        historyManager2.add(task);

        String resultOfTheMethods = historyManager1.getHistory().toString();
        String expectedResult = historyManager2.getHistory().toString();

        Assertions.assertEquals(expectedResult, resultOfTheMethods, "Некорректно работает метод add. " +
                "Происходит дублирование");
    }

    @Test
    void checkEmptyTaskHistory() { //проверка пустой истории задач (как в техническом задании попросили)
        String expectedResult = "[]";

        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        String resultOfTheMethods = historyManager.getHistory().toString();

        Assertions.assertEquals(expectedResult, resultOfTheMethods, "История задач не пустая");
    }
}