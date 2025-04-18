import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.HistoryManager;
import project.Managers;
import project.TaskManager;

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