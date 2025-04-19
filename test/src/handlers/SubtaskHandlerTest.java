package handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import project.HttpTaskServer;
import project.InMemoryTaskManager;
import project.TaskManager;
import com.google.gson.Gson;
import project.task.Epic;
import project.task.Status;
import project.task.Subtask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubtaskHandlerTest {
    private final TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
    Gson gson = HttpTaskServer.getGson();

    public SubtaskHandlerTest() throws IOException {
    }

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

    @Test
    void checkAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask newSubtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        String subtask = gson.toJson(newSubtask);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(subtask))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, httpResponse.statusCode(), "Код ответа неверный");

        List<Subtask> listSubtasks = taskManager.gettingListOfAllSubtask();

        assertNotNull(listSubtasks, "Подзадачи не возвращаются");
        assertEquals(1, listSubtasks.size(), "Некорректное количество подзадач");
        assertEquals(newSubtask.getNameTask(), listSubtasks.get(0).getNameTask(), "Некорректное имя подзадачи");
    }

    @Test
    void checkUpdateTask() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask oldSubtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        taskManager.addNewSubtask(oldSubtask);

        Subtask newSubtask = new Subtask("Обновлённая первая подзадача",
                "Описание первой подзадачи", 1, Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        String subtask = gson.toJson(newSubtask);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(subtask))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, httpResponse.statusCode(), "Код ответа неверный");

        List<Subtask> listTasks = taskManager.gettingListOfAllSubtask();

        assertNotNull(listTasks, "Подзадачи не возвращаются");
        assertEquals(1, listTasks.size(), "Некорректное количество подзадач");
        assertEquals("Обновлённая первая подзадача", listTasks.get(0).getNameTask(),
                "Некорректное имя подзадачи");
    }

    @Test
    void checkDeleteByIdSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        taskManager.addNewSubtask(subtask);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");

        List<Subtask> listTasks = taskManager.gettingListOfAllSubtask();

        assertEquals(new ArrayList<>(), listTasks, "Задача не удалена");
    }

    @Test
    void checkGettingListOfAllSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        Subtask subtask2 = new Subtask("Вторая подзадача",
                "Описание второй подзадачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 9, 0), 0);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        String expectedString = gson.toJson(taskManager.gettingListOfAllSubtask());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получен неправильный списка всех задач");
    }

    @Test
    void checkGetSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Первая подзадача",
                "Описание первой подзадачи", Status.IN_PROGRESS, 45,
                LocalDateTime.of(2025, 4, 4, 8, 0), 0);
        taskManager.addNewSubtask(subtask1);
        String expectedString = gson.toJson(taskManager.getSubtask(1));

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получена неправильная задача");
    }
}