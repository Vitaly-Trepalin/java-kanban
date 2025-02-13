package test.task;

import main.task.Epic;
import main.task.Status;
import main.task.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    @Test
    void checkingEqualityOfClassInstancesSubtask() {
        Epic epic = new Epic("Второй эпик", "Описание второго эпика");
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание первой подзадачи", 10,
                Status.DONE, epic);
        Subtask subtask2 = new Subtask("Первая подзадача", "Описание первой подзадачи",10,
                Status.DONE, epic);

        Assertions.assertTrue(subtask1.equals(subtask2), "Подзадачи не равны друг другу");
    }

    @Test
    void checkGetEpic() {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        Subtask subtask = new Subtask("Первая подзадача", "Описание первой подзадачи", epic);

        Epic resultOfTheMethod = subtask.getEpic();

        Assertions.assertEquals(epic, resultOfTheMethod, "получен неверный эпик");
    }
}