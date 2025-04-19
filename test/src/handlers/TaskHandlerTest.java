package handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import project.HttpTaskServer;
import project.InMemoryTaskManager;
import project.TaskManager;
import com.google.gson.Gson;
import project.task.Status;
import project.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskHandlerTest {
    private final TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
    Gson gson = HttpTaskServer.getGson();

    @BeforeEach
    public void setUp() {
        taskManager.deletingTasks();
        taskManager.deletingEpics();
        httpTaskServer.start();
    }

    @AfterEach
    public void shutDown() {
        httpTaskServer.stop();
    }

    public TaskHandlerTest() throws IOException {
    }

    @Test
    void checkAddTask() throws IOException, InterruptedException {
        Task newTask = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        String task = gson.toJson(newTask);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(task))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, httpResponse.statusCode(), "Код ответа неверный");

        List<Task> listTasks = taskManager.gettingListOfAllTasks();

        assertNotNull(listTasks, "Задачи не возвращаются");
        assertEquals(1, listTasks.size(), "Некорректное количество задач");
        assertEquals(newTask.getNameTask(), listTasks.get(0).getNameTask(), "Некорректное имя задачи");
    }

    @Test
    void checkUpdateTask() throws IOException, InterruptedException {
        Task oldTask = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        taskManager.addNewTask(oldTask);

        Task newTask = new Task("Обновлённая первая задача", "Описание первой задачи", 0,
                Status.NEW, 30, LocalDateTime.of(2025, 4, 4, 6, 0));
        String task = gson.toJson(newTask);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(task))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, httpResponse.statusCode(), "Код ответа неверный");

        List<Task> listTasks = taskManager.gettingListOfAllTasks();

        assertNotNull(listTasks, "Задачи не возвращаются");
        assertEquals(1, listTasks.size(), "Некорректное количество задач");
        assertEquals("Обновлённая первая задача", listTasks.get(0).getNameTask(),
                "Некорректное имя задачи");
    }

    @Test
    void checkDeleteByIdTask() throws IOException, InterruptedException {
        Task task = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        taskManager.addNewTask(task);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/0");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");

        List<Task> listTasks = taskManager.gettingListOfAllTasks();

        assertEquals(new ArrayList<>(), listTasks, "Задача не удалена");
    }

    @Test
    void checkGettingListOfAllTasks() throws IOException, InterruptedException {
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                40, LocalDateTime.of(2025, 4, 4, 7, 0));
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        String expectedString = gson.toJson(taskManager.gettingListOfAllTasks());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получен неправильный списка всех задач");
    }

    @Test
    void checkGetTask() throws IOException, InterruptedException {
        Task task = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        taskManager.addNewTask(task);
        String expectedString = gson.toJson(taskManager.getTask(0));

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/0");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получена неправильная задача");
    }
}
