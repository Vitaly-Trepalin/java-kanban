package main;

import main.task.Epic;
import main.task.Status;
import main.task.Subtask;
import main.task.Task;

public class Main {
    static InMemoryTaskManager inMemoryTaskManager;

    public static void main(String[] args) {
        System.out.println("Создание 3 задач, 2 эпиков и 3 подзадач");
        inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("Первая задача", "123");
        Task task2 = new Task("Вторая задача", "456");
        Task task3 = new Task("Третья задача", "789");

        Epic epic1 = new Epic("Первый эпик", "11111111111");
        Epic epic2 = new Epic("Второй эпик", "22222222222");

        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", epic1);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", epic1);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", epic2);

        inMemoryTaskManager.addNewTask(task1, Status.NEW);
        inMemoryTaskManager.addNewTask(task2, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task3, Status.DONE);

        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);

        inMemoryTaskManager.addNewSubtask(subtask1, Status.IN_PROGRESS);
        inMemoryTaskManager.addNewSubtask(subtask2, Status.DONE);
        inMemoryTaskManager.addNewSubtask(subtask3, Status.NEW);

        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка вывода всех подзадач эпика");
        System.out.println(inMemoryTaskManager.gettingAllSubtasks(epic1)); //проверка вывода всех подзадач эпика

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка обновления задачи с id=2");
        Task task = new Task("Обновленная третья задача", "Новое описание", 2, Status.NEW);
        inMemoryTaskManager.taskUpdate(task); //блок проверки обновления задачи с id=2"
        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка удаления подзадачи по id");
        inMemoryTaskManager.deleteByIdSubtask(5); //блок проверки после удаления подзадачи по id
        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка обновления подзадачи с id=6");
        // блок проверки обновления подзадачи с id=6
        Subtask subtask4 = new Subtask("Четвёртая подзадача", "8888888888", epic1);
        subtask4.setId(6);
        subtask4.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.subtaskUpdate(subtask4);

        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка после удаления эпика по id=3");
        inMemoryTaskManager.deleteByIdEpic(3); //блок проверки после удаления эпика по id
        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка после удаления всех подзадач");
        inMemoryTaskManager.deletingSubtask(); //блок проверки после удаления всех подзадач
        printAllTasksSubtasksEpics();

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка получения задачи по id=1 и эпика по id=4");
        System.out.println(inMemoryTaskManager.getTask(1)); //блок проверки получения задач по id
        System.out.println(inMemoryTaskManager.getEpic(4));

        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Проверка записи истории просмотра задач и их получение из main.InMemoryHistoryManager");
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        System.out.println(inMemoryHistoryManager.getHistory());
    }

    public static void printAllTasksSubtasksEpics() {
        System.out.println(inMemoryTaskManager.gettingListOfAllTasks());
        System.out.println(inMemoryTaskManager.gettingListOfAllEpic());
        System.out.println(inMemoryTaskManager.gettingListOfAllSubtask());
    }
}
