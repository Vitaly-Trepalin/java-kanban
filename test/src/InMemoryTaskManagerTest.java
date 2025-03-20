import org.junit.jupiter.api.*;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach //инициализация статических полей taskManager
    public void creatingAndFillingClassInMemoryTaskManager() {
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS);
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        Epic epic = new Epic("Первый эпик", "Описание первого эпика", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 2);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", Status.IN_PROGRESS, 2);
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание третьей подзадачи",
                Status.DONE, 2);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
    }

    @AfterEach //удаление статических полей taskManager
    public void deleteAllData() {
        taskManager.deletingTasks();
        taskManager.deletingEpics();
        taskManager.deletingSubtask();
        InMemoryTaskManager.setId(0);
    }

    @Test
    void checkDeletingTasks() {
        Map<Integer, Task> expectedHashMap = new HashMap<>();

        taskManager.deletingTasks();
        Map<Integer, Task> resultOfTheMethod = taskManager.getTasks();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица c задачами не отчистилась");
    }

    @Test
    void checkDeletingEpic() {
        HashMap<Integer, Epic> expectedHashMap = new HashMap<>();

        taskManager.deletingEpics();
        Map<Integer, Epic> resultOfTheMethod = taskManager.getEpics();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица с эпиками не отчистилась");
    }

    @Test
    void checkDeletingSubtask() {
        HashMap<Integer, Subtask> expectedHashMap = new HashMap<>();

        taskManager.deletingSubtask();
        Map<Integer, Subtask> resultOfTheMethod = taskManager.getSubtasks();

        Assertions.assertEquals(expectedHashMap, resultOfTheMethod, "Хеш-таблица с подзадачами не " +
                "отчистилась");
    }

    @Test
    void checkAddNewTaskAndGettingTaskByNumber() {

        Task expectedTask = new Task("Третья задача", "Описание третьей задачи", 6, Status.NEW);

        Task task3 = new Task("Третья задача", "Описание третьей задачи", Status.NEW);
        taskManager.addNewTask(task3);
        Task resultOfTheMethod = taskManager.getTask(6);

        Assertions.assertEquals(expectedTask, resultOfTheMethod, "Задача была некорректно создана или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewEpicAndGettingEpicByNumber() {
        Epic expectedEpic = new Epic("Второй эпик", "Описание второго эпика", 6, Status.NEW,
                new ArrayList<>());
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);

        taskManager.addNewEpic(epic);
        Epic resultOfTheMethod = taskManager.getEpic(6);

        Assertions.assertEquals(expectedEpic, resultOfTheMethod, "Эпик был некорректно создан или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewSubtaskAndGettingSubtaskByNumber() {
        Subtask expectedSubtask = new Subtask("Четвёртая подзадача", "Описание четвёртой подзадачи",
                6, Status.NEW, 2);

        Subtask subtask = new Subtask("Четвёртая подзадача", "Описание четвёртой подзадачи",
                6, Status.NEW, 2);
        taskManager.addNewSubtask(subtask);
        Subtask resultOfTheMethod = taskManager.getSubtask(6);

        Assertions.assertEquals(expectedSubtask, resultOfTheMethod, "Подзадача была некорректно создана " +
                "или некорректно работает получение по Id");
    }

    @Test
    void checkTaskUpdate() {
        Task expectedTask = new Task("Обновлённая первая задача", "Описание первой задачи",
                0, Status.NEW);

        taskManager.taskUpdate(expectedTask);
        Task resultOfTheMethod = taskManager.getTask(0);

        Assertions.assertEquals(expectedTask, resultOfTheMethod, "Задача некорректно обновилась");
    }

    @Test
    void checkEpicUpdate() {
        Epic expectedEpic = new Epic("Обновлённый второй эпик", "Описание второго эпика", 2,
                Status.NEW, new ArrayList<>());

        taskManager.epicUpdate(expectedEpic);
        Epic resultOfTheMethod = taskManager.getEpic(2);

        Assertions.assertEquals(expectedEpic, resultOfTheMethod, "Эпик некорректно обновился");
    }

    @Test
    void checkSubtaskUpdate() {
        Subtask expectedSubtask = new Subtask("Обновлённая третья подзадача",
                "Описание третьей подзадачи", 5, Status.DONE, 2);

        taskManager.subtaskUpdate(expectedSubtask);
        Subtask resultOfTheMethod = taskManager.getSubtask(5);

        Assertions.assertEquals(expectedSubtask, resultOfTheMethod, "Подзадача некорректно обновилась");
    }

    @Test
    void checkGettingListOfAllTasks() {
        Task expectedTask1 = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW);
        Task expectedTask2 = new Task("Вторая задача",
                "Описание второй задачи", 1, Status.IN_PROGRESS);
        List<Task> expectedTaskList = new ArrayList<>(Arrays.asList(expectedTask1, expectedTask2));

        List<Task> resultOfTheMethod = taskManager.gettingListOfAllTasks();

        Assertions.assertEquals(expectedTaskList, resultOfTheMethod, "Ошибка при получении списка задач");
    }

    @Test
    void checkGettingListOfAllEpics() {
        Epic expectedEpic1 = new Epic("Первый эпик",
                "Описание первого эпика", 2, Status.NEW, new ArrayList<>());
        List<Epic> expectedEpicsList = new ArrayList<>(Arrays.asList(expectedEpic1));

        List<Epic> resultOfTheMethod = taskManager.gettingListOfAllEpic();

        Assertions.assertEquals(expectedEpicsList, resultOfTheMethod, "Ошибка при получении списка эпиков");
    }

    @Test
    void checkGettingListOfAllSubtasks() {
        Subtask expectedSubtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 3, Status.NEW, 2);
        Subtask expectedSubtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", 4, Status.IN_PROGRESS, 2);
        Subtask expectedSubtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", 5, Status.DONE, 2);
        List<Subtask> expectedSubtasksList = new ArrayList<>(Arrays.asList(expectedSubtask1, expectedSubtask2,
                expectedSubtask3));

        List<Subtask> resultOfTheMethod = taskManager.gettingListOfAllSubtask();

        Assertions.assertEquals(expectedSubtasksList, resultOfTheMethod, "Ошибка при получении списка " +
                "подзадач");
    }
}