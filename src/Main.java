import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Первая задача", "123");
        Task task2 = new Task("Вторая задача", "456");
        Task task3 = new Task("Третья задача", "789");

        Epic epic1 = new Epic("Первый эпик", "11111111111");
        Epic epic2 = new Epic("Первый эпик", "22222222222");

        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", );
        Subtask subtask1 = new Subtask();
        Subtask subtask1 = new Subtask();
    }
}
