package test;

import main.InMemoryHistoryManager;
import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void checkAddAndGettingTask() {
        Task expectedTask = new Task("Первая задача", "Описание первой задачи", 10, Status.NEW);

        historyManager.add(expectedTask);
        Task resultOfTheMethods = historyManager.getHistory().getFirst();

        Assertions.assertTrue(expectedTask.equals(resultOfTheMethods), "некорректно работает добавление " +
                "задачи или получение её из истории");

    }
}