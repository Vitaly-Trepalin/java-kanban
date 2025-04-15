import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static TaskManager taskManager = Managers.getDefault();

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new SubtaskHandler());
        httpServer.createContext("/epic", new EpicsHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedHandler());

        httpServer.start();
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static void setTaskManager(TaskManager taskManager) {
        HttpTaskServer.taskManager = taskManager;
    }
}
