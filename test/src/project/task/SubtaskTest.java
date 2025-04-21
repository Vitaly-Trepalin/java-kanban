package project.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

class SubtaskTest {

    @Test
    void checkingEqualityOfClassInstancesSubtask() {
        Subtask actualSubtask = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, 30, LocalDateTime.of(2025, 4, 4, 9, 0),
                0);
        Subtask expectedSubtask = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, 30, LocalDateTime.of(2025, 4, 4, 9, 0),
                0);

        Assertions.assertEquals(actualSubtask, expectedSubtask, "Подзадачи не равны друг другу");
    }

    @Test
    void checkGetEpic() {
        Subtask subtask = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 15, LocalDateTime.of(2025, 4, 4, 8, 0),
                0);

        int resultOfTheMethod = subtask.getEpicId();

        Assertions.assertEquals(0, resultOfTheMethod, "получен неверный эпик");
    }

    @Test
    void CheckForARelatedEpic() {
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", 10, new ArrayList<>());
        int expectedId = epic.getId();

        Subtask subtask = new Subtask("Первая подзадача", "Описание первой подзадачи",
                Status.NEW, 15, LocalDateTime.of(2025, 4, 4, 8, 0),
                10);
        int actualId = subtask.getEpicId();

        Assertions.assertEquals(expectedId, actualId, "получен неверный ID эпика");
    }
}