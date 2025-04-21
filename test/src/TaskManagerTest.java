import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.InMemoryTaskManager;
import project.TaskManager;
import project.task.Epic;
import project.task.Status;
import project.task.Subtask;
import project.task.Task;

import java.time.LocalDateTime;
import java.util.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    public InMemoryTaskManager creatingAndFillingClassInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                40, LocalDateTime.of(2025, 4, 4, 7, 0));
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 15, LocalDateTime.of(2025, 4, 4, 8, 0),
                2);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание второй подзадачи",
                Status.IN_PROGRESS, 30, LocalDateTime.of(2025, 4, 4, 9,
                0),
                2);
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание третьей подзадачи",
                Status.DONE, 45, LocalDateTime.of(2025, 4, 4, 10, 0),
                2);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        return taskManager;
    }

    @Test
    void checkDeletingTasks() {
        Map<Integer, Task> expectedHashMap = new HashMap<>();

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.deletingTasks();
        Map<Integer, Task> resultOfTheMethod = taskManager.getTasks();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица c задачами не отчистилась");
    }

    @Test
    void checkDeletingEpic() {
        HashMap<Integer, Epic> expectedHashMap = new HashMap<>();

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.deletingEpics();
        Map<Integer, Epic> resultOfTheMethod = taskManager.getEpics();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица с эпиками не отчистилась");
    }

    @Test
    void checkDeletingSubtask() {
        HashMap<Integer, Subtask> expectedHashMap = new HashMap<>();

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.deletingSubtask();
        Map<Integer, Subtask> resultOfTheMethod = taskManager.getSubtasks();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица с подзадачами не " +
                "отчистилась");
    }

    @Test
    void checkAddNewTaskAndGettingTaskByNumber() {

        Task expectedTask = new Task("Третья задача", "Описание третьей задачи", 6, Status.NEW,
                30, LocalDateTime.of(2025, 4, 4, 10, 0));

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Task task3 = new Task("Третья задача", "Описание третьей задачи", Status.NEW,
                30, LocalDateTime.of(2025, 4, 4, 13, 0));
        taskManager.addNewTask(task3);
        Task resultOfTheMethod = taskManager.getTask(6);

        Assertions.assertEquals(expectedTask, resultOfTheMethod, "Задача была некорректно создана или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewEpicAndGettingEpicByNumber() {
        Epic expectedEpic = new Epic("Второй эпик", "Описание второго эпика", 6,
                new ArrayList<>());
        Epic epic = new Epic("Второй эпик", "Описание второго эпика");

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.addNewEpic(epic);
        Epic resultOfTheMethod = taskManager.getEpic(6);

        Assertions.assertEquals(expectedEpic, resultOfTheMethod, "Эпик был некорректно создан или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewSubtaskAndGettingSubtaskByNumber() {
        Subtask expectedSubtask = new Subtask("Четвёртая подзадача", "Описание четвёртой подзадачи",
                6, Status.NEW, 60, LocalDateTime.of(2025, 4, 4, 12, 0),
                2);

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Subtask subtask = new Subtask("Четвёртая подзадача", "Описание четвёртой подзадачи",
                6, Status.NEW, 60, LocalDateTime.of(2025, 4, 4, 12, 0),
                2);
        taskManager.addNewSubtask(subtask);
        Subtask resultOfTheMethod = taskManager.getSubtask(6);

        Assertions.assertEquals(expectedSubtask, resultOfTheMethod, "Подзадача была некорректно создана " +
                "или некорректно работает получение по Id");
    }

    @Test
    void checkTaskUpdate() {
        Task expectedTask = new Task("Обновлённая первая задача", "Описание первой задачи",
                0, Status.NEW, 30, LocalDateTime.of(2025, 4, 4, 16, 0));

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.taskUpdate(expectedTask);
        Task resultOfTheMethod = taskManager.getTask(0);

        Assertions.assertEquals(expectedTask, resultOfTheMethod, "Задача некорректно обновилась");
    }

    @Test
    void checkEpicUpdate() {
        Epic expectedEpic = new Epic("Обновлённый второй эпик", "Описание второго эпика", 2,
                new ArrayList<>());

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.epicUpdate(expectedEpic);
        Epic resultOfTheMethod = taskManager.getEpic(2);

        Assertions.assertEquals(expectedEpic, resultOfTheMethod, "Эпик некорректно обновился");
    }

    @Test
    void checkSubtaskUpdate() {
        Subtask expectedSubtask = new Subtask("Обновлённая третья подзадача",
                "Описание третьей подзадачи", 5, Status.DONE, 60,
                LocalDateTime.of(2025, 4, 4, 12, 0), 2);

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        taskManager.subtaskUpdate(expectedSubtask);
        Subtask resultOfTheMethod = taskManager.getSubtask(5);

        Assertions.assertEquals(expectedSubtask, resultOfTheMethod, "Подзадача некорректно обновилась");
    }

    @Test
    void checkGettingListOfAllTasks() {
        Task expectedTask1 = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW,
                30, LocalDateTime.of(2025, 4, 4, 6, 0));
        Task expectedTask2 = new Task("Вторая задача", "Описание второй задачи", 1,
                Status.IN_PROGRESS, 40, LocalDateTime.of(2025, 4, 4, 7, 0));
        List<Task> expectedTaskList = new ArrayList<>(Arrays.asList(expectedTask1, expectedTask2));

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        List<Task> resultOfTheMethod = taskManager.gettingListOfAllTasks();

        Assertions.assertEquals(expectedTaskList, resultOfTheMethod, "Ошибка при получении списка задач");
    }

    @Test
    void checkGettingListOfAllEpics() {
        Epic expectedEpic1 = new Epic("Первый эпик",
                "Описание первого эпика", 2, new ArrayList<>());
        List<Epic> expectedEpicsList = new ArrayList<>(Arrays.asList(expectedEpic1));

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        List<Epic> resultOfTheMethod = taskManager.gettingListOfAllEpic();

        Assertions.assertEquals(expectedEpicsList, resultOfTheMethod, "Ошибка при получении списка эпиков");
    }

}
