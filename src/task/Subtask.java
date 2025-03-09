package task;

public class Subtask extends Task {

    private Epic epic; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameSubtask, String description, Status status, Epic epic) {
        super(nameSubtask, description, status);
        this.epic = epic;
    }

    public Subtask(String name, String description, int id, Status status, Epic epic) {
        super(name, description, id, status);
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
        return "Subtask{" +
                "name='" + getNameTask() + '\'' +
                ", Description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
