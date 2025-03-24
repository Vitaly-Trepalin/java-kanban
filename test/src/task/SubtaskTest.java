package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    @Test
    void checkingEqualityOfClassInstancesSubtask() {
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, 0);
        Subtask subtask2 = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, 0);

        Assertions.assertEquals(subtask1, subtask2, "Подзадачи не равны друг другу");
    }

    @Test
    void checkGetEpic() {
        Subtask subtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.NEW, 0);

        int resultOfTheMethod = subtask.getEpicId();

        Assertions.assertEquals(0, resultOfTheMethod, "получен неверный эпик");
    }
}