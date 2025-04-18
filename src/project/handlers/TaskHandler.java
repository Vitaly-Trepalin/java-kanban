package project.handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.handlers.typeAdapters.DurationTypeAdapter;
import project.handlers.typeAdapters.LocalDateTimeTypeAdapter;
import project.task.Task;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case ADD -> addNewTaskHandler(exchange);
            case DELETE_ID -> deleteByIdTaskHandler(exchange);
            case GET_ALL -> gettingListOfAllTasksHandler(exchange);
            case GET_ID -> getTaskHandler(exchange);
            default -> sendBadRequest(exchange, "Некорректный запрос. Такого эндпоинта не существует!");
        }
    }

    private void addNewTaskHandler(HttpExchange exchange) throws IOException {
        Gson gson = getGson();
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(body, Task.class);

        JsonElement jsonElement = JsonParser.parseString(body);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (!jsonObject.has("id")) { //если id не указан, то добавление задачи, иначе обновление
            try {
                taskManager.addNewTask(task);
            } catch (RuntimeException e) {
                sendHasInteractions(exchange, e.getMessage()); //пересечение времени выполнения задач
            }
            sendStatusCode(exchange);
        } else {
            try {
                taskManager.taskUpdate(task);
            } catch (RuntimeException e) {
                sendNotFound(exchange, e.getMessage()); //отсутствует задача для обновления с таким id
            }
            sendStatusCode(exchange);
        }
    }

    private void deleteByIdTaskHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int taskId = Integer.parseInt(uri.getPath().split("/")[2]);
        taskManager.deleteByIdTask(taskId);
        sendText(exchange, "Задача с id=" + taskId + " успешно удалена");
    }

    private void gettingListOfAllTasksHandler(HttpExchange exchange) throws IOException {
        Gson gson = getGson();
        String taskList = gson.toJson(taskManager.gettingListOfAllTasks());
        sendText(exchange, taskList);
    }

    private void getTaskHandler(HttpExchange exchange) throws IOException {
        Gson gson = getGson();
        URI uri = exchange.getRequestURI();
        int taskId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            String task = gson.toJson(taskManager.getTask(taskId));
            sendText(exchange, task);
        } catch (NullPointerException e) {
            sendNotFound(exchange, "Такая задача не существует");
        }
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .setPrettyPrinting()
                .create();
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        if (path.length == 2) {
            switch (requestMethod) {
                case "GET" -> {
                    return Endpoint.GET_ALL;
                }
                case "POST" -> {
                    return Endpoint.ADD;
                }
            }
        }
        if (path.length == 3) {
            switch (requestMethod) {
                case "GET" -> {
                    return Endpoint.GET_ID;
                }
                case "DELETE" -> {
                    return Endpoint.DELETE_ID;
                }
            }
        }
        return Endpoint.UNKNOWN;
    }

    private enum Endpoint {
        GET_ALL,
        GET_ID,
        DELETE_ID,
        ADD,
        UNKNOWN
    }
}
