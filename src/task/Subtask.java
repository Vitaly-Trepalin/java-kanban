package task;

import java.time.LocalDateTime;

import static task.Type.SUBTASK;

public class Subtask extends Task {

    private int epicId; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameSubtask, String description, Status status, long duration, LocalDateTime startTime,
                   int epicId) {
        super(nameSubtask, description, status, duration, startTime);
        super.setType(SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int id, Status status, long duration, LocalDateTime startTime,
                   int epicId) {
        super(name, description, id, status, duration, startTime);
        super.setType(SUBTASK);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() + ","
                + getType() + ","
                + getNameTask() + ","
                + getStatus() + ","
                + getDescription() + ","
                + getDuration().toMinutes() + ","
                + getStartTime() + ","
                + epicId;
    }
}
