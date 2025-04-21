package project;

import java.nio.file.Path;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(Path.of("C:\\Users\\admin\\Desktop\\file.txt").toFile());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
