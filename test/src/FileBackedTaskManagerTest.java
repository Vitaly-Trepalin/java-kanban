import org.junit.jupiter.api.*;
import task.Status;
import task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileBackedTaskManagerTest {

    @Test
    @Order(2)
    void checkSavingTasks() throws IOException {
        String expectedTask = "0,TASK,Первая задача,NEW,Описание первой задачи,";

        File file = File.createTempFile("test", ".csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW);
        fileBackedTaskManager.addNewTask(task1); // при выполнении этого метода сработает метод save() класса
        // FileBackedTaskManager

        String resultOfMethod = Files.readString(file.toPath()).split("\n")[1];

        file.deleteOnExit();
        Assertions.assertEquals(expectedTask, resultOfMethod, "Строковое представление объектов не совпадает." +
                " Запись объекта в файл работает некорректно");
    }

    @Test
    @Order(3)
    void checkLoadFromFileTasks() throws IOException {
        File file = File.createTempFile("test", ".csv");
        String allTasks = "id,type,name,status,description,epic" +
                "\n0,TASK,Первая задача,NEW,Описание первой задачи," +
                "\n1,EPIC,Первый эпик,IN_PROGRESS,Описание первого эпика," +
                "\n2,SUBTASK,Первая подзадача,IN_PROGRESS,Описание первой подзадачи,1";
        Files.writeString(file.toPath(), allTasks, StandardOpenOption.TRUNCATE_EXISTING);
        String expectedResult = "0,TASK,Первая задача,NEW,Описание первой задачи," +
                "1,EPIC,Первый эпик,IN_PROGRESS,Описание первого эпика," +
                "2,SUBTASK,Первая подзадача,IN_PROGRESS,Описание первой подзадачи,1";

        FileBackedTaskManager fileBackedTaskManager = FileBackedTaskManager.loadFromFile(file);
        String resultOfTheMethod = "" + fileBackedTaskManager.gettingListOfAllTasks() +
                fileBackedTaskManager.gettingListOfAllEpic() + fileBackedTaskManager.gettingListOfAllSubtask();
        resultOfTheMethod = resultOfTheMethod.replaceAll("[\\[\\]]", "");

        file.deleteOnExit();
        Assertions.assertEquals(expectedResult, resultOfTheMethod, "Строковое представление объектов не " +
                "совпадает. Загрузка объектов из файла работает некорректно");
    }

    @Test
    @Order(1)
    void checkSavingAndLoadingAnEmptyFile() throws IOException {
        String expectedString = "id,type,name,status,description,epic\n";

        File file = File.createTempFile("test", ".csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.save();

        String resultOfMethod = Files.readString(file.toPath());

        file.deleteOnExit();
        Assertions.assertEquals(expectedString, resultOfMethod, "Сохранение и загрузка пустого файла " +
                "работает некорректно");
    }
}
