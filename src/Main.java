import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Первая задача", "123");
        Task task2 = new Task("Вторая задача", "456");
        Task task3 = new Task("Третья задача", "789");

        Epic epic1 = new Epic("Первый эпик", "11111111111");
        Epic epic2 = new Epic("Второй эпик", "22222222222");

        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", epic1);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", epic1);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", epic2);

        taskManager.creationTask(task1, Status.NEW);
        taskManager.creationTask(task2, Status.IN_PROGRESS);
        taskManager.creationTask(task3, Status.DONE);

        taskManager.creationEpic(epic1);
        taskManager.creationEpic(epic2);

        taskManager.creationSubtask(subtask1, Status.IN_PROGRESS);
        taskManager.creationSubtask(subtask2, Status.DONE);
        taskManager.creationSubtask(subtask3, Status.NEW);

        System.out.println(taskManager.gettingListOfAllTasks()); //блок проверки всех задач после создания различных
        // типов задач
        System.out.println(taskManager.gettingListOfAllEpic());
        System.out.println(taskManager.gettingListOfAllSubtask());

        System.out.println("-----------------------------------------------------------------------");

        System.out.println(taskManager.gettingAllSubtasks(epic1)); //проверка вывода всех подзадач эпика

        System.out.println("-----------------------------------------------------------------------");

        taskManager.deleteByIdSubtask(5); //блок проверки после удаления подзадачи по id
        System.out.println(taskManager.gettingListOfAllTasks());
        System.out.println(taskManager.gettingListOfAllEpic());
        System.out.println(taskManager.gettingListOfAllSubtask());

        System.out.println("-----------------------------------------------------------------------");

        // блок проверки обновления подзадачи с id=6
        Subtask subtask4 = new Subtask("Четвёртая подзадача", "8888888888", epic2);
        subtask4.setId(6);
        subtask4.setStatus(Status.IN_PROGRESS);

        taskManager.subtaskUpdate(subtask4);

        System.out.println(taskManager.gettingListOfAllTasks());
        System.out.println(taskManager.gettingListOfAllEpic());
        System.out.println(taskManager.gettingListOfAllSubtask());

        System.out.println("-----------------------------------------------------------------------");

        taskManager.deleteByIdEpic(3); //блок проверки после удаления эпика по id
        System.out.println(taskManager.gettingListOfAllTasks());
        System.out.println(taskManager.gettingListOfAllEpic());
        System.out.println(taskManager.gettingListOfAllSubtask());

        System.out.println("-----------------------------------------------------------------------");

        taskManager.deletingSubtask(); //блок проверки после удаления всех подзадач
        System.out.println(taskManager.gettingListOfAllTasks());
        System.out.println(taskManager.gettingListOfAllEpic());
        System.out.println(taskManager.gettingListOfAllSubtask());

        System.out.println("-----------------------------------------------------------------------");

        System.out.println(taskManager.getByIdTask(1)); //блок проверки получения задач по id
        System.out.println(taskManager.getByIdEpics(4));

    }
}
