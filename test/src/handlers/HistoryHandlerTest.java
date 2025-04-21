package handlers;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.HttpTaskServer;
import project.InMemoryTaskManager;
import project.TaskManager;
import project.task.Status;
import project.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryHandlerTest {
    private final TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
    Gson gson = HttpTaskServer.getGson();

    public HistoryHandlerTest() throws IOException {
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
    void checkGetHistory() throws IOException, InterruptedException {
        Task task1 = new Task("Первая задача", "Описание первой задачи", Status.NEW, 30,
                LocalDateTime.of(2025, 4, 4, 6, 0));
        Task task2 = new Task("Вторая задача", "Описание второй задачи", Status.IN_PROGRESS,
                40, LocalDateTime.of(2025, 4, 4, 7, 0));
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        String expectedOrder = gson.toJson(taskManager.gettingListOfAllTasks().reversed());

        taskManager.getTask(1);
        taskManager.getTask(0);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/history");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, httpResponse.statusCode(), "Код ответа неверный");
        assertEquals(expectedOrder, httpResponse.body(), "Получена неправильная история просмотра задач");
    }
}
