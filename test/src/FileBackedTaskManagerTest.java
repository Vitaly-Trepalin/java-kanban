import project.FileBackedTaskManager;
import project.TaskManager;
import project.exception.ManagerSaveException;
import org.junit.jupiter.api.*;
import project.task.Epic;
import project.task.Status;
import project.task.Subtask;
import project.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class FileBackedTaskManagerTest extends TaskManagerTest<TaskManager> {

    private final File file = File.createTempFile("test", ".csv");

    public FileBackedTaskManagerTest() throws IOException {
    }

    public FileBackedTaskManager creatingAndFillingClassFileBackedTaskManager() {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                40, LocalDateTime.of(2025, 4, 4, 7, 00));
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 15, null, 2);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание второй подзадачи",
                Status.IN_PROGRESS, 30, LocalDateTime.of(2025, 4, 4, 9, 0),
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
    void checkSavingTasks() throws IOException {
        String expectedTask = "0,TASK,Первая задача,NEW,Описание первой задачи,30,2025-04-04T06:00";

        FileBackedTaskManager taskManager = creatingAndFillingClassFileBackedTaskManager();
        taskManager.save();
        String resultOfMethod = Files.readString(file.toPath()).split("\n")[1];

        file.deleteOnExit();
        Assertions.assertEquals(expectedTask, resultOfMethod, "Строковое представление объектов не " +
                "совпадает. Запись объекта в файл работает некорректно");
    }

    @Test
    void checkLoadFromFileTasks() {
        String expectedResult =
                "[0,TASK,Первая задача,NEW,Описание первой задачи,30,2025-04-04T06:00, " +
                        "1,TASK,Вторая задача,IN_PROGRESS,Описание второй задачи,40,2025-04-04T07:00]" +
                        "[2,EPIC,Первый эпик,IN_PROGRESS,Описание первого эпика,90,2025-04-04T09:00]" +
                        "[3,SUBTASK,Первая подзадача,NEW,Описание первой подзадачи,15,null,2, " +
                        "4,SUBTASK,Вторая подзадача,IN_PROGRESS,Описание второй подзадачи,30,2025-04-04T09:00,2, " +
                        "5,SUBTASK,Третья подзадача,DONE,Описание третьей подзадачи,45,2025-04-04T10:00,2]";

        FileBackedTaskManager taskManager = creatingAndFillingClassFileBackedTaskManager();
        taskManager.save();
        taskManager = FileBackedTaskManager.loadFromFile(file);

        String resultOfTheMethod = "" + taskManager.gettingListOfAllTasks() +
                taskManager.gettingListOfAllEpic() + taskManager.gettingListOfAllSubtask();

        file.deleteOnExit();
        Assertions.assertEquals(expectedResult, resultOfTheMethod, "Строковое представление объектов не " +
                "совпадает. Загрузка объектов из файла работает некорректно");
    }

    @Test
    void checkSavingAndLoadingAnEmptyFile() throws IOException {
        String expectedString = "id,type,name,status,description,duration,startTime,epic\n";

        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        taskManager.save();

        String resultOfMethod = Files.readString(file.toPath());

        file.deleteOnExit();
        Assertions.assertEquals(expectedString, resultOfMethod, "Сохранение и загрузка пустого файла " +
                "работает некорректно");
    }

    @Test
    void checkThrowAnException() {
        File file1 = new File("D:/file1.txt");
        Assertions.assertThrows(ManagerSaveException.class, () -> {
            FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file1);
            Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                    LocalDateTime.of(2025, 4, 4, 6, 0));
            fileBackedTaskManager.addNewTask(task1);
            fileBackedTaskManager.save();
        }, "Неправильно указан путь файла для сохранения");
    }
}
