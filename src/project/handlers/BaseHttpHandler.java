package project.handlers;

import com.sun.net.httpserver.HttpExchange;
import project.HttpTaskServer;
import project.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    TaskManager taskManager = HttpTaskServer.getTaskManager();

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    protected void sendStatusCode(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    protected void sendBadRequest(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(400, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(406, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    protected void sendErrorSavingDataToFile(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(500, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }
}
