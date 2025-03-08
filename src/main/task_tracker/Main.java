package main.task_tracker;

import main.task_tracker.task.Epic;
import main.task_tracker.task.Status;
import main.task_tracker.task.Subtask;
import main.task_tracker.task.Task;

public class Main {
    private static InMemoryTaskManager inMemoryTaskManager;
    private static final InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    public static void main(String[] args) {

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("Создание 3 задач, 2 эпиков и 3 подзадач\n");
        inMemoryTaskManager = new InMemoryTaskManager();

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

        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка вывода всех подзадач эпика. Вывод подзадач первого эпика\n");
        System.out.println(inMemoryTaskManager.gettingAllSubtasks(epic1)); //проверка вывода всех подзадач эпика

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка обновления задачи. Обновление третьей задачи с id=2\n");
        Task task = new Task("Обновленная третья задача",
                "Новое описание третьей задачи", 2, Status.NEW);
        inMemoryTaskManager.taskUpdate(task); //блок проверки обновления задачи с id=2
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления подзадачи по id. Удаление первой подзадачи с id=5\n");
        inMemoryTaskManager.deleteByIdSubtask(5); //блок проверки после удаления подзадачи по id
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка обновления подзадачи. Обновление подзадачи с id=6\n");
        // блок проверки обновления подзадачи с id=6
        Subtask subtask4 = new Subtask("Четвёртая подзадача",
                "Описание четвёртой подзадачи", epic1);
        subtask4.setId(6);
        subtask4.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.subtaskUpdate(subtask4);

        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка факта удаления подзадач после удаления первого эпика с id=3\n");
        inMemoryTaskManager.deleteByIdEpic(3); //блок проверки после удаления эпика по id
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления всех подзадач\n");
        inMemoryTaskManager.deletingSubtask(); //блок проверки после удаления всех подзадач
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка добавления подзадачи в второго эпика\n");
        Subtask subtask5 = new Subtask("Пятая подзадача", "Описание пятой подзадачи", epic2);
        inMemoryTaskManager.addNewSubtask(subtask5, Status.IN_PROGRESS);
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка получения:\n 1. второй задачи с id=1\n 2. второго эпика с id=4\n 3. " +
                "первой задачи с id=0\n 4. пятой подзадачи с id=8\n");
        System.out.println(inMemoryTaskManager.getTask(1)); //блок проверки получения задач по id
        System.out.println(inMemoryTaskManager.getEpic(4));
        System.out.println(inMemoryTaskManager.getTask(0));
        System.out.println(inMemoryTaskManager.getSubtask(8));

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка записи (в том числе порядка) истории просмотра задач и их получение из " +
                "InMemoryHistoryManager\n");
        System.out.println(inMemoryHistoryManager.getHistory());

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления из истории InMemoryHistoryManager просмотра первой задачи id=0 после " +
                "удаления самой задачи\n");
        inMemoryTaskManager.deleteByIdTask(0);
        System.out.println(inMemoryHistoryManager.getHistory());

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления из истории InMemoryHistoryManager просмотра второго эпика id=4 и его " +
                "подзадач после удаления эпика\n");
        inMemoryTaskManager.deleteByIdEpic(4);
        System.out.println(inMemoryHistoryManager.getHistory());
    }

    public static void printAllTasksSubtasksEpics() {
        System.out.println(inMemoryTaskManager.gettingListOfAllTasks());
        System.out.println(inMemoryTaskManager.gettingListOfAllEpic());
        System.out.println(inMemoryTaskManager.gettingListOfAllSubtask());
    }
}
