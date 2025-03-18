package task;
import static task.Type.SUBTASK;

public class Subtask extends Task {

    private Epic epic; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameSubtask, String description, Status status, Epic epic) {
        super(nameSubtask, description, status);
        super.setType(SUBTASK);
        this.epic = epic;
    }

    public Subtask(String name, String description, int id, Status status, Epic epic) {
        super(name, description, id, status);
        super.setType(SUBTASK);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return getId() + ","
                + getType() + ","
                + getNameTask() + ","
                + getStatus() + ","
                + getDescription() + ","
                + getEpic().getId();
    }
}
