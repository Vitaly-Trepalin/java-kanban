package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case ADD -> addNewTaskHandler(exchange);
            case UPDATE -> taskUpdateHandler(exchange);
            case DELETE_ALL -> deletingTasksHandler(exchange);
            case DELETE_ID -> deleteByIdTaskHandler(exchange);
            case GET_ALL -> gettingListOfAllTasksHandler(exchange);
            case GET_ID -> getTaskHandler(exchange);
            default -> System.out.println("Такого эндпоинта не существует!");
        }

    }

    public void addNewTaskHandler(HttpExchange exchange) {
        
    }

    public void taskUpdateHandler(HttpExchange exchange) {

    }

    public void deletingTasksHandler(HttpExchange exchange) {

    }

    public void deleteByIdTaskHandler(HttpExchange exchange) {

    }

    public void gettingListOfAllTasksHandler(HttpExchange exchange) {

    }

    public void getTaskHandler(HttpExchange exchange) {

    }


    public Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        if (path.length == 2) {
            switch (requestMethod) {
                case "GET" -> {
                    return Endpoint.GET_ALL;
                }
                case "DELETE" -> {
                    return Endpoint.DELETE_ALL;
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
                case "POST" -> {
                    return Endpoint.UPDATE;
                }
            }
        }
        return Endpoint.UNKNOWN;
    }

    enum Endpoint {
        GET_ALL,
        GET_ID,
        DELETE_ALL,
        DELETE_ID,
        ADD,
        UPDATE,
        UNKNOWN
    }
}
