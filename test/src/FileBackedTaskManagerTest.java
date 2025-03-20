import org.junit.jupiter.api.*;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileBackedTaskManagerTest {

    private File file = File.createTempFile("test", ".csv");
    private FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

    public FileBackedTaskManagerTest() throws IOException {
    }

    @BeforeEach //инициализация статических полей taskManager
    public void creatingAndFillingClassFileBackedTaskManager() {
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
    void checkSavingTasks() throws IOException {
        String expectedTask = "0,TASK,Первая задача,NEW,Описание первой задачи,";

        taskManager.save();
        String resultOfMethod = Files.readString(file.toPath()).split("\n")[1];

        file.deleteOnExit();
        Assertions.assertEquals(expectedTask, resultOfMethod, "Строковое представление объектов не совпадает." +
                " Запись объекта в файл работает некорректно");
    }

    @Test
    void checkLoadFromFileTasks() throws IOException {
        String expectedResult =
                "[0,TASK,Первая задача,NEW,Описание первой задачи,, " +
                        "1,TASK,Вторая задача,IN_PROGRESS,Описание второй задачи,]" +
                        "[2,EPIC,Первый эпик,IN_PROGRESS,Описание первого эпика,]" +
                        "[3,SUBTASK,Первая подзадача,NEW,Описание первой подзадачи,2, " +
                        "4,SUBTASK,Вторая подзадача,IN_PROGRESS,Описание второй подзадачи,2, " +
                        "5,SUBTASK,Третья подзадача,DONE,Описание третьей подзадачи,2]";

        taskManager.save();
        FileBackedTaskManager.loadFromFile(file);
        String resultOfTheMethod = "" + taskManager.gettingListOfAllTasks() +
                taskManager.gettingListOfAllEpic() + taskManager.gettingListOfAllSubtask();

        file.deleteOnExit();
        Assertions.assertEquals(expectedResult, resultOfTheMethod, "Строковое представление объектов не " +
                "совпадает. Загрузка объектов из файла работает некорректно");
    }

    @Test
    void checkSavingAndLoadingAnEmptyFile() throws IOException {
        deleteAllData();
        String expectedString = "id,type,name,status,description,epic\n";

        taskManager.save();

        String resultOfMethod = Files.readString(file.toPath());

        file.deleteOnExit();
        Assertions.assertEquals(expectedString, resultOfMethod, "Сохранение и загрузка пустого файла " +
                "работает некорректно");
    }
}
