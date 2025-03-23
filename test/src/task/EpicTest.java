package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EpicTest {

    private List<Subtask> preparationForTestingMethods() {
        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", 1, Status.DONE, 0);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", 2, Status.DONE, 0);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", 3, Status.DONE, 0);
        return List.of(subtask1, subtask2, subtask3);
    }

    @Test
    void checkingEqualityOfClassInstancesEpic() {
        Epic expectedEpic = new Epic("Второй эпик",
                "Описание второго эпика", 10, Status.DONE, new ArrayList<>());
        Epic actualEpic = new Epic("Второй эпик",
                "Описание второго эпика", 10, Status.DONE, new ArrayList<>());

        Assertions.assertEquals(expectedEpic, actualEpic, "Эпики не равны друг другу");
    }

    @Test
    void checkSetStatus() {
        List<Subtask> listSubtask = preparationForTestingMethods();
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, Status.NEW, listSubtask);
        Status expectedStatus = Status.DONE;

        epic1.setStatus();
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkGetSubtasks() {
        List<Subtask> listSubtask = preparationForTestingMethods();
        Epic epic = new Epic("Первый эпик",
                "Описание первого эпика", 10, Status.NEW, listSubtask);

        List<Subtask> resultOfTheMethod = epic.getSubtasks();

        Assertions.assertEquals(listSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }

    @Test
    void checkSetSubtasks() {
        List<Subtask> listSubtask = preparationForTestingMethods();
        Epic epic = new Epic("Второй эпик",
                "Описание второго эпика", 0, Status.NEW, new ArrayList<>());
        epic.setSubtasks(listSubtask);
        List<Subtask> resultOfTheMethod = epic.getSubtasks();

        Assertions.assertEquals(listSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }
}