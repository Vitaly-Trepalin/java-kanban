import org.junit.jupiter.api.*;
import project.InMemoryTaskManager;
import project.TaskManager;
import project.task.Status;
import project.task.Subtask;

import java.time.LocalDateTime;
import java.util.*;

class InMemoryTaskManagerTest extends TaskManagerTest<TaskManager> {

    @Test
    void checkGettingListOfAllSubtasks() {
        Subtask expectedSubtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 3, Status.NEW, 15,
                LocalDateTime.of(2025, 4, 4, 8, 0), 2);
        Subtask expectedSubtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", 4, Status.IN_PROGRESS, 30,
                LocalDateTime.of(2025, 4, 4, 9, 0), 2);
        Subtask expectedSubtask3 = new Subtask("Третья подзадача",
                "Описание третьей подзадачи", 5, Status.DONE, 45,
                LocalDateTime.of(2025, 4, 4, 10, 0), 2);
        List<Subtask> expectedSubtasksList = new ArrayList<>(Arrays.asList(expectedSubtask1, expectedSubtask2,
                expectedSubtask3));

        InMemoryTaskManager taskManager = creatingAndFillingClassInMemoryTaskManager();
        List<Subtask> resultOfTheMethod = taskManager.gettingListOfAllSubtask();

        Assertions.assertEquals(expectedSubtasksList, resultOfTheMethod, "Ошибка при получении списка " +
                "подзадач");
    }

    @Test
    void testAreThereAnyIntersections() {
        Subtask subtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", 3, Status.NEW, 15,
                LocalDateTime.of(2025, 4, 4, 9, 0), 2);

        InMemoryTaskManager inMemoryTaskManager = creatingAndFillingClassInMemoryTaskManager();
        boolean resultOfMethod = inMemoryTaskManager.hasIntersections(subtask);

        Assertions.assertTrue(resultOfMethod, "метод areThereAnyIntersections не находит совпадения во " +
                "времени выполнения задач");
    }
}