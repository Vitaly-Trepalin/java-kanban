import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {

    @Test
    void checkGetDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefault();

        Assertions.assertNotNull(taskManager, "менеджер задач не проинициализирован");
    }

    @Test
    void checkGetDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        Assertions.assertNotNull(historyManager, "менеджер истории не проинициализирован");
    }
}