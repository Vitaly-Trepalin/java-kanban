package test.task;

import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void checkingEqualityOfClassInstancesTask() {
        Task task1 = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);
        Task task2 = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);

        Assertions.assertTrue(task1.equals(task2), "Задачи не равны друг другу");
    }

    @Test
    void checkGetId() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);
        int expectedId = 10;

        int resultOfTheMethod = task.getId();

        Assertions.assertEquals(expectedId, resultOfTheMethod, "получаемое id не верно");
    }

    @Test
    void checkSetId() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);
        int expectedId = 12;

        task.setId(12);
        int resultOfTheMethod = task.getId();

        Assertions.assertEquals(expectedId, resultOfTheMethod, "id установилось не верно");
    }

    @Test
    void checkGetStatus() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);
        Status expectedStatus = Status.NEW;

        Status resultOfTheMethod = task.getStatus();

        Assertions.assertEquals(expectedStatus, resultOfTheMethod, "получен неверный статус");
    }

    @Test
    void checkSetStatus() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.DONE);
        Status expectedStatus = Status.NEW;

        task.setStatus(Status.NEW);
        Status resultOfTheMethod = task.getStatus();

        Assertions.assertEquals(expectedStatus, resultOfTheMethod, "id установилось не верно");
    }
}