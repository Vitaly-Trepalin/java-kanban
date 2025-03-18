package task;

import java.util.ArrayList;
import java.util.List;
import static task.Type.EPIC;


public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String nameEpic, String description, Status status) {
        super(nameEpic, description, status);
        super.setStatus(Status.NEW);
        super.setType(EPIC);
        subtasks = new ArrayList<>();
    }

    public Epic(String nameEpic, String description, int id, Status status, List<Subtask> subtasks) {
        super(nameEpic, description, id, status);
        super.setType(EPIC);
        this.subtasks = subtasks;
    }

    public void setStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
        }

        int numberOfSubtasksWithStatusDone = 0; //блок в котором считаем количество подзадач с разным статусом
        int numberOfSubtasksWithStatusNew = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.DONE)) {
                numberOfSubtasksWithStatusDone++;
            }
            if (subtask.getStatus().equals(Status.NEW)) {
                numberOfSubtasksWithStatusNew++;
            }
        }

        if (numberOfSubtasksWithStatusNew == subtasks.size()) {
            setStatus(Status.NEW);
        } else if (numberOfSubtasksWithStatusDone == subtasks.size()) {
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
