package tasks;

public class Subtask extends Task {

    private Epic epic; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameTask, String description, Epic epic) {
        super(nameTask, description);
        this.epic= epic;
    }

    public int getEpicId() {
        return epicId;
    }
}
