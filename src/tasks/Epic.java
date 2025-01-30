package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String nameTask, String description) {
        super(nameTask, description);
    }

    public void setStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
        }

        int numberOfCompletedTasks = 0; //блок в котором считаем количество подзадач с разным статусом
        int numberOfSubtasksWithStatus = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.DONE)) {
                numberOfCompletedTasks++;
            }
            if (subtask.getStatus().equals(Status.NEW)) {
                numberOfSubtasksWithStatus++;
            }
        }

        if (numberOfCompletedTasks == subtasks.size()) {
            setStatus(Status.DONE);
        } else if (numberOfSubtasksWithStatus == subtasks.size()) {
            setStatus(Status.NEW);
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

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", subtasks=" + subtasks +
                '}';
    }
}
