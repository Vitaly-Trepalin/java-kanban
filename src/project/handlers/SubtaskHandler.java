package project.handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.exception.AdditionAndUpdateException;
import project.exception.ManagerSaveException;
import project.task.Subtask;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case ADD -> addNewSubtaskHandler(exchange);
            case DELETE_ID -> deleteByIdSubtaskHandler(exchange);
            case GET_ALL -> gettingListOfAllSubtasksHandler(exchange);
            case GET_ID -> getSubtaskHandler(exchange);
            default -> sendBadRequest(exchange, "Некорректный запрос. Такого эндпоинта не существует!");
        }
    }

    private void addNewSubtaskHandler(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(body, Subtask.class);

        JsonElement jsonElement = JsonParser.parseString(body);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (!jsonObject.has("id")) { //если id не указан, то добавление подзадачи, иначе обновление
            try {
                taskManager.addNewSubtask(subtask);
            } catch (AdditionAndUpdateException e) {
                sendHasInteractions(exchange, e.getMessage()); //пересечение времени выполнения задач или отсутствует
                // требуемый эпик
            } catch (ManagerSaveException e) {
                sendErrorSavingDataToFile(exchange, e.getMessage());
            }
            sendStatusCode(exchange);
        } else {
            try {
                taskManager.subtaskUpdate(subtask);
            } catch (AdditionAndUpdateException e) {
                sendNotFound(exchange, e.getMessage()); //отсутствует подзадача для обновления с таким id
            } catch (ManagerSaveException e) {
                sendErrorSavingDataToFile(exchange, e.getMessage());
            }
            sendStatusCode(exchange);
        }
    }

    private void deleteByIdSubtaskHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int taskId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            taskManager.deleteByIdSubtask(taskId);
        } catch (ManagerSaveException e) {
            sendErrorSavingDataToFile(exchange, e.getMessage());
        }
        sendText(exchange, "Подзадача с id=" + taskId + " успешно удалена");
    }

    private void gettingListOfAllSubtasksHandler(HttpExchange exchange) throws IOException {
        String taskList = gson.toJson(taskManager.gettingListOfAllSubtask());
        sendText(exchange, taskList);
    }

    private void getSubtaskHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int taskId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            String subtask = gson.toJson(taskManager.getSubtask(taskId));
            sendText(exchange, subtask);
        } catch (NullPointerException e) {
            sendNotFound(exchange, "Такая подзадача не существует");
        }
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
