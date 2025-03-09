import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;


class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    public static InMemoryTaskManager creatingAndFillingClassInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Первая задача", "123", Status.NEW);
        Task task2 = new Task("Вторая задача", "456", Status.IN_PROGRESS);
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", Status.NEW, epic);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "4444444444", Status.IN_PROGRESS, epic);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", Status.DONE, epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        return taskManager;
    }

    @Test
    void checkDeletingTasks() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        Map<Integer, Task> expectedHashMap = new HashMap<>();

        taskManager.deletingTasks();
        Map<Integer, Task> resultOfTheMethod = taskManager.getTasks();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod), "Хеш-таблица c задачами не отчистилась");
    }

    @Test
    void checkDeletingEpic() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        HashMap<Integer, Epic> expectedHashMap = new HashMap<>();

        taskManager.deletingEpics();
        Map<Integer, Epic> resultOfTheMethod = taskManager.getEpics();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod), "Хеш-таблица с эпиками не отчистилась");
    }

    @Test
    void checkDeletingSubtask() {
        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        HashMap<Integer, Subtask> expectedHashMap = new HashMap<>();

        taskManager.deletingSubtask();
        Map<Integer, Subtask> resultOfTheMethod = taskManager.getSubtasks();

        Assertions.assertTrue(expectedHashMap.equals(resultOfTheMethod), "Хеш-таблица с подзадачами не " +
                "отчистилась");
    }


    @Test
    void checkAddNewTaskAndGettingTaskByNumber() {
        Task expectedTask = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW);

        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW);
        inMemoryTaskManager.addNewTask(task1);

        HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.remove(0);

        Task resultOfTheMethod = inMemoryTaskManager.getTask(0);

        Assertions.assertTrue(expectedTask.equals(resultOfTheMethod), "Задача была некорректно создана или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewEpicAndGettingEpicByNumber() {

        Epic expectedEpic = new Epic("Второй эпик", "Описание второго эпика", 0, Status.NEW,
                new ArrayList<>());
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);

        inMemoryTaskManager.addNewEpic(epic);
        Epic resultOfTheMethod = inMemoryTaskManager.getEpic(0);

        Assertions.assertTrue(expectedEpic.equals(resultOfTheMethod), "Эпик был некорректно создан или " +
                "некорректно работает получение по Id");
    }

    @Test
    void checkAddNewSubtaskAndGettingSubtaskByNumber() {
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", 0, Status.NEW,
                new ArrayList<>());
        Subtask expectedSubtask = new Subtask("Первая подзадача", "Описание первой подзадачи", 1,
                Status.NEW, epic);

        inMemoryTaskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.NEW, epic);

        inMemoryTaskManager.addNewSubtask(subtask);
        Subtask resultOfTheMethod = inMemoryTaskManager.getSubtask(1);

        Assertions.assertTrue(expectedSubtask.equals(resultOfTheMethod), "Подзадача была некорректно создана " +
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

        Assertions.assertTrue(expectedSubtask.equals(resultOfTheMethod), "Подзадача некорректно обновилась");
    }

    @Test
    void checkGettingListOfAllTasks() {
        Task expectedTask1 = new Task("Первая задача", "Описание первой задачи", 0, Status.NEW);
        Task expectedTask2 = new Task("Вторая задача",
                "Описание второй задачи", 1, Status.IN_PROGRESS);
        List<Task> expectedTaskList = new ArrayList<>(Arrays.asList(expectedTask1, expectedTask2));

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS);
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);

        List<Task> resultOfTheMethod = taskManager.gettingListOfAllTasks();

        Assertions.assertEquals(expectedTaskList, resultOfTheMethod, "Ошибка при получении списка задач");
    }

    @Test
    void checkGettingListOfAllEpics() {
        Epic expectedEpic1 = new Epic("Первый эпик",
                "Описание первого эпика", 0, Status.NEW, new ArrayList<>());
        Epic expectedEpic2 = new Epic("Второй эпик",
                "Описание второго эпика", 1, Status.IN_PROGRESS, new ArrayList<>());
        List<Epic> expectedEpicsList = new ArrayList<>(Arrays.asList(expectedEpic1, expectedEpic2));

        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика", Status.NEW);
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика", Status.IN_PROGRESS);
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);

        List<Epic> resultOfTheMethod = taskManager.gettingListOfAllEpic();

        Assertions.assertEquals(expectedEpicsList, resultOfTheMethod, "Ошибка при получении списка эпиков");
    }

    @Test
    void checkGettingListOfAllSubtasks() {
        Epic expectedEpic1 = new Epic("Первый эпик",
                "Описание первого эпика", 0, Status.NEW, new ArrayList<>());
        Subtask expectedSubtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 1, Status.NEW, expectedEpic1);
        Subtask expectedSubtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", 2, Status.IN_PROGRESS, expectedEpic1);
        List<Subtask> expectedSubtasksList = new ArrayList<>(Arrays.asList(expectedSubtask1, expectedSubtask2));

        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика", Status.NEW);
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", Status.IN_PROGRESS, epic1);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        List<Subtask> resultOfTheMethod = taskManager.gettingListOfAllSubtask();

        Assertions.assertEquals(expectedSubtasksList, resultOfTheMethod, "Ошибка при получении списка " +
                "подзадач");
    }
}