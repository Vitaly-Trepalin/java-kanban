package main;

import main.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static List<Task> browsingHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        browsingHistory.add(task);
        if (browsingHistory.size() > 10) {
            browsingHistory.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return browsingHistory;
    }
}
