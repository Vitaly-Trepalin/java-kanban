package task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EpicTest {
    Epic epic;
    List<Subtask> listSubtask;

    @BeforeEach
    void preparationForTestingMethods() {
        epic = new Epic("Второй эпик", "Описание второго эпика", Status.NEW);
        Subtask subtask1 = new Subtask("Первая подзадача", "3333333333", 1, Status.DONE, epic);
        Subtask subtask2 = new Subtask("Вторая подзадача", "4444444444", 2, Status.DONE, epic);
        Subtask subtask3 = new Subtask("Третья подзадача", "5555555555", 3, Status.DONE, epic);
        listSubtask = List.of(subtask1, subtask2, subtask3);
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
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, Status.NEW, listSubtask);
        Status expectedStatus = Status.DONE;

        epic1.setStatus();
        Status resultOfTheMethod = epic1.getStatus();

        Assertions.assertSame(expectedStatus, resultOfTheMethod, "статус высчитан неверно");
    }

    @Test
    void checkGetSubtasks() {
        Epic epic1 = new Epic("Первый эпик",
                "Описание первого эпика", 10, Status.NEW, listSubtask);

        List<Subtask> resultOfTheMethod = epic1.getSubtasks();

        Assertions.assertEquals(listSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }

    @Test
    void checkSetSubtasks() {
        epic.setSubtasks(listSubtask);
        List<Subtask> resultOfTheMethod = epic.getSubtasks();

        Assertions.assertEquals(listSubtask, resultOfTheMethod, "списки подзадач не одинаковы");
    }
}