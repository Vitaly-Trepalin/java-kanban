package handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {


    protected void sendText(HttpExchange exchange, String text) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    protected void sendNotFound() {

    }

    protected void sendHasInteractions() {

    }
}
