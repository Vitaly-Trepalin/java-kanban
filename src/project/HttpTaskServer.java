package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import project.handlers.*;
import project.handlers.typeAdapters.DurationTypeAdapter;
import project.handlers.typeAdapters.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static TaskManager taskManager;
    private final HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        HttpTaskServer.taskManager = taskManager;
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
    }

    public static void main(String[] args) throws IOException {
        new HttpTaskServer(Managers.getDefault()).start();
    }

    public void start() {
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new SubtaskHandler());
        httpServer.createContext("/epic", new EpicsHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedHandler());
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .setPrettyPrinting()
                .create();
    }
}



