package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TaskTest {

    @Test
    void checkingEqualityOfClassInstancesTask() {
        Task task1 = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW,
                40, LocalDateTime.of(2025, 4, 4, 7, 00));

        Assertions.assertEquals(task1, task2, "Задачи не равны друг другу");
    }

    @Test
    void checkGetId() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        int expectedId = 10;

        int resultOfTheMethod = task.getId();

        Assertions.assertEquals(expectedId, resultOfTheMethod, "получаемое id не верно");
    }

    @Test
    void checkSetId() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        int expectedId = 12;

        task.setId(12);
        int resultOfTheMethod = task.getId();

        Assertions.assertEquals(expectedId, resultOfTheMethod, "id установилось не верно");
    }

    @Test
    void checkGetStatus() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Status expectedStatus = Status.NEW;

        Status resultOfTheMethod = task.getStatus();

        Assertions.assertEquals(expectedStatus, resultOfTheMethod, "получен неверный статус");
    }

    @Test
    void checkSetStatus() {
        Task task = new Task("Первая задача", "Описание первой задачи", 10, Status.DONE, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Status expectedStatus = Status.NEW;

        task.setStatus(Status.NEW);
        Status resultOfTheMethod = task.getStatus();

        Assertions.assertEquals(expectedStatus, resultOfTheMethod, "id установилось не верно");
    }
}