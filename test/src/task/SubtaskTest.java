package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    @Test
    void checkingEqualityOfClassInstancesSubtask() {
        Epic epic = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, epic);
        Subtask subtask2 = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, epic);

        Assertions.assertEquals(subtask1, subtask2, "Подзадачи не равны друг другу");
    }

    @Test
    void checkGetEpic() {
        Epic expectedEpic = new Epic("Первый эпик", "Описание первого эпика", Status.NEW);
        Subtask subtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.NEW, expectedEpic);

        Epic resultOfTheMethod = subtask.getEpic();

        Assertions.assertEquals(expectedEpic, resultOfTheMethod, "получен неверный эпик");
    }
}