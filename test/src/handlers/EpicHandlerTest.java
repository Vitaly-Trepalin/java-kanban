package handlers;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.HttpTaskServer;
import project.InMemoryTaskManager;
import project.TaskManager;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EpicHandlerTest {
    private final TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
    Gson gson = HttpTaskServer.getGson();

    public EpicHandlerTest() throws IOException {
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
    void checkAddEpic() throws IOException, InterruptedException {
        Epic newEpic = new Epic("Первый эпик", "Описание первого эпика");
        String epic = gson.toJson(newEpic);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(epic))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, httpResponse.statusCode(), "Код ответа неверный");

        List<Epic> listEpics = taskManager.gettingListOfAllEpic();

        assertNotNull(listEpics, "Эпики не возвращаются");
        assertEquals(1, listEpics.size(), "Некорректное количество эпиков");
        assertEquals(newEpic.getNameTask(), listEpics.get(0).getNameTask(), "Некорректное имя эпика");
    }

    @Test
    void checkDeleteByIdEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic/0");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");

        List<Epic> listTasks = taskManager.gettingListOfAllEpic();

        assertEquals(new ArrayList<>(), listTasks, "Эпик не удален");
    }

    @Test
    void checkGettingListOfAllEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);
        String expectedString = gson.toJson(taskManager.gettingListOfAllEpic());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получен неправильный списка всех эпиков");
    }

    @Test
    void checkGetEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addNewEpic(epic1);
        String expectedString = gson.toJson(taskManager.getEpic(0));

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic/0");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получен неправильный эпик");
    }

    @Test
    void checkGetSubtasksEpic() throws IOException, InterruptedException {
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
        String expectedString = gson.toJson(taskManager.getEpic(0).getSubtasks());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic/0/subtasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedString, httpResponse.body(), "Получен неправильный эпик");
    }
}
