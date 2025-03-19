package task;
import static task.Type.SUBTASK;

public class Subtask extends Task {

    private int epicId; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameSubtask, String description, Status status, int epicId) {
        super(nameSubtask, description, status);
        super.setType(SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
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
                + epicId;
    }
}
