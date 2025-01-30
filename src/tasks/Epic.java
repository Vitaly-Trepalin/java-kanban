package tasks;

import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String nameTask, String description) {
        super(nameTask, description);
    }

    public void setStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
        }

        int numberOfCompletedTasks = 0;
        for (Task task : subtasks) {
            if (task.getStatus().equals(Status.DONE)) {
                numberOfCompletedTasks++;
            }
        }
        if (numberOfCompletedTasks == subtasks.size()) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }

    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
