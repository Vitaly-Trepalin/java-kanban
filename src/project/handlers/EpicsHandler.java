package project.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.exception.ManagerSaveException;
import project.task.Epic;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case ADD -> addNewEpicHandler(exchange);
            case DELETE_ID -> deleteByIdEpicHandler(exchange);
            case GET_ALL -> gettingListOfAllEpicsHandler(exchange);
            case GET_ID -> getEpicHandler(exchange);
            case GET_SUBTASKS -> getSubtasksEpicHandler(exchange);
            default -> sendBadRequest(exchange, "Некорректный запрос. Такого эндпоинта не существует!");
        }
    }

    private void addNewEpicHandler(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(body, Epic.class);
        try {
            taskManager.addNewEpic(epic);
        } catch (ManagerSaveException e) {
            sendErrorSavingDataToFile(exchange, e.getMessage());
        }
        sendStatusCode(exchange);
    }

    private void deleteByIdEpicHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int epicId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            taskManager.deleteByIdEpic(epicId);
        } catch (ManagerSaveException e) {
            sendErrorSavingDataToFile(exchange, e.getMessage());
        }
        sendText(exchange, "Эпик с id=" + epicId + " успешно удален");
    }

    private void gettingListOfAllEpicsHandler(HttpExchange exchange) throws IOException {
        String taskList = gson.toJson(taskManager.gettingListOfAllEpic());
        sendText(exchange, taskList);
    }

    private void getEpicHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int epicId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            String epic = gson.toJson(taskManager.getEpic(epicId));
            sendText(exchange, epic);
        } catch (NullPointerException e) {
            sendNotFound(exchange, "Такой эпик не существует");
        }
    }

    private void getSubtasksEpicHandler(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        int epicId = Integer.parseInt(uri.getPath().split("/")[2]);
        try {
            Epic epic = taskManager.getEpic(epicId);
            String subtasks = gson.toJson(epic.getSubtasks());
            sendText(exchange, subtasks);
        } catch (NullPointerException | IOException e) {
            sendNotFound(exchange, "Такой эпик не существует");
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
        if (path.length == 4 && requestMethod.equals("GET")) {
            return Endpoint.GET_SUBTASKS;
        }
        return Endpoint.UNKNOWN;
    }

    private enum Endpoint {
        GET_ALL,
        GET_ID,
        GET_SUBTASKS,
        DELETE_ID,
        ADD,
        UNKNOWN
    }
}
