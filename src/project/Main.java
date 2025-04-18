package project;

import project.task.Epic;
import project.task.Status;
import project.task.Subtask;
import project.task.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    private static FileBackedTaskManager fileBackedTaskManager;

    public static void main(String[] args) throws IOException {
        File file = File.createTempFile("test", ".txt");

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("Создание 3 задач, 2 эпиков и 3 подзадач\n");
        fileBackedTaskManager = new FileBackedTaskManager(file);

        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                40, LocalDateTime.of(2025, 4, 4, 7, 0));
        Task task3 = new Task("Третья задача", "Описание третьей задачи", Status.DONE, 45,
                LocalDateTime.of(2025, 4, 4, 11, 0));
        fileBackedTaskManager.addNewTask(task1);
        fileBackedTaskManager.addNewTask(task2);
        fileBackedTaskManager.addNewTask(task3);

        //создание 2 эпиков
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        fileBackedTaskManager.addNewEpic(epic1);
        fileBackedTaskManager.addNewEpic(epic2);

        //создание 3 подзадач
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 3);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 9, 0), 3);
        Subtask subtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", Status.NEW, 45,
                LocalDateTime.of(2025, 4, 4, 10, 0), 4);
        fileBackedTaskManager.addNewSubtask(subtask1);
        fileBackedTaskManager.addNewSubtask(subtask2);
        fileBackedTaskManager.addNewSubtask(subtask3);

        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка вывода всех задач и подзадач в отсортированном порядке\n");
        System.out.println(fileBackedTaskManager.getPrioritizedTasks());

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка вывода всех подзадач эпика. Вывод подзадач первого эпика\n");
        System.out.println(fileBackedTaskManager.gettingAllSubtasks(epic1)); //проверка вывода всех подзадач эпика

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка обновления задачи. Обновление третьей задачи с id=2\n");
        Task task = new Task("Обновленная третья задача",
                "Новое описание третьей задачи", 2, Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 11, 0));

        fileBackedTaskManager.taskUpdate(task); //блок проверки обновления задачи с id=2

        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления подзадачи по id. Удаление первой подзадачи с id=5\n");
        fileBackedTaskManager.deleteByIdSubtask(5); //блок проверки после удаления подзадачи по id
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка обновления второй подзадачи. Обновление подзадачи с id=6\n");
        // блок проверки обновления подзадачи с id=6
        Subtask subtask4 = new Subtask("Обновлённая вторая подзадача",
                "Описание обновлённой второй подзадачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 9, 0), 3);
        subtask4.setId(6);
        subtask4.setStatus(Status.IN_PROGRESS);

        fileBackedTaskManager.subtaskUpdate(subtask4);

        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка факта удаления подзадач после удаления первого эпика с id=3\n");
        fileBackedTaskManager.deleteByIdEpic(3); //блок проверки после удаления эпика по id
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления всех подзадач\n");
        fileBackedTaskManager.deletingSubtask(); //блок проверки после удаления всех подзадач
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка добавления подзадачи второго эпика\n");
        Subtask subtask5 = new Subtask("Пятая подзадача",
                "Описание пятой подзадачи", Status.IN_PROGRESS, 10,
                LocalDateTime.of(2025, 4, 4, 13, 0), 4);
        fileBackedTaskManager.addNewSubtask(subtask5);
        printAllTasksSubtasksEpics();

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка получения:\n 1. второй задачи с id=1\n 2. второго эпика с id=4\n 3. " +
                "первой задачи с id=0\n 4. пятой подзадачи с id=8\n");
        System.out.println(fileBackedTaskManager.getTask(1)); //блок проверки получения задач по id
        System.out.println(fileBackedTaskManager.getEpic(4));
        System.out.println(fileBackedTaskManager.getTask(0));
        System.out.println(fileBackedTaskManager.getSubtask(8));

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка записи (в том числе порядка) истории просмотра задач и их получение из " +
                "project.InMemoryHistoryManager\n");
        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления из истории project.InMemoryHistoryManager просмотра первой задачи id=0 после " +
                "удаления самой задачи\n");
        fileBackedTaskManager.deleteByIdTask(0);
        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());

        System.out.println("\n-----------------------------------------------------------------------");

        System.out.println("Проверка удаления из истории project.InMemoryHistoryManager просмотра второго эпика id=4 и его " +
                "подзадач после удаления эпика\n");
        fileBackedTaskManager.deleteByIdEpic(4);
        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());

        file.deleteOnExit();
    }

    public static void printAllTasksSubtasksEpics() {
        System.out.println(fileBackedTaskManager.gettingListOfAllTasks());
        System.out.println(fileBackedTaskManager.gettingListOfAllEpic());
        System.out.println(fileBackedTaskManager.gettingListOfAllSubtask());
    }

}
