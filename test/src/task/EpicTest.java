package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EpicTest {

    private List<Subtask> preparationForTestingMethods(String status) {
        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", 1, Status.NEW,
                15, LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", 2, Status.NEW,
                30, LocalDateTime.of(2025, 4, 4, 9, 0), 0);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", 3, Status.NEW,
                45, LocalDateTime.of(2025, 4, 4, 10, 0), 0);
        List<Subtask> subtaskList = List.of(subtask1, subtask2, subtask3);
        Subtask subtask4 = new Subtask("Четвёртая подзадача", "6666666666", 4, Status.DONE,
                45, LocalDateTime.of(2025, 4, 4, 14, 0), 0);
        Subtask subtask5 = new Subtask("Пятая подзадача", "7777777777", 4, Status.DONE,
                45, LocalDateTime.of(2025, 4, 4, 14, 0), 0);
        List<Subtask> subtaskList2 = List.of(subtask4);
        return switch (status) {
            case "DONE" -> subtaskList2;
            case "IN_PROGRESS" -> subtaskList.stream()
                    .peek(subtask -> subtask.setStatus(Status.IN_PROGRESS))
                    .collect(Collectors.toList());
            case "NEW&&DONE" -> Stream.concat(subtaskList.stream(), subtaskList2.stream())
                    .collect(Collectors.toList());
            default -> subtaskList;
        };
    }

    @Test
    void checkingEqualityOfClassInstancesEpic() {
        Epic expectedEpic = new Epic("Второй эпик",
                "Описание второго эпика", 10, new ArrayList<>());
        Epic actualEpic = new Epic("Второй эпик",
                "Описание второго эпика", 10, new ArrayList<>());

        Assertions.assertEquals(expectedEpic, actualEpic, "Эпики не равны друг другу");
    }

    @Test
    void checkSetStatusNew() {
        List<Subtask> listSubtask = preparationForTestingMethods("NEW");
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, listSubtask);
        Status expectedStatus = Status.NEW;

        epic1.setStatus(epic1.setStatusForEpic());
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkSetStatusDone() {
        List<Subtask> listSubtask = preparationForTestingMethods("DONE");
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, listSubtask);
        Status expectedStatus = Status.DONE;

        epic1.setStatus(epic1.setStatusForEpic());
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkSetStatusInProgress() {
        List<Subtask> listSubtask = preparationForTestingMethods("IN_PROGRESS");
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, listSubtask);
        Status expectedStatus = Status.IN_PROGRESS;

        epic1.setStatus(epic1.setStatusForEpic());
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkSetStatusNewAndDone() {
        List<Subtask> listSubtask = preparationForTestingMethods("NEW&&DONE");
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, listSubtask);
        Status expectedStatus = Status.IN_PROGRESS;

        epic1.setStatus(epic1.setStatusForEpic());
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkGetSubtasks() {
        List<Subtask> expectedListSubtask = preparationForTestingMethods("NEW&&DONE");
        Epic epic = new Epic("Первый эпик",
                "Описание первого эпика", 10, expectedListSubtask);

        List<Subtask> resultOfTheMethod = epic.getSubtasks();

        Assertions.assertEquals(expectedListSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }

    @Test
    void checkSetSubtasks() {
        List<Subtask> expectedListSubtask = preparationForTestingMethods("NEW&&DONE");
        Epic epic = new Epic("Второй эпик",
                "Описание второго эпика", 0, new ArrayList<>());
        epic.setSubtasks(expectedListSubtask);
        List<Subtask> resultOfTheMethod = epic.getSubtasks();

        Assertions.assertEquals(expectedListSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }
}