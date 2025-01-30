package tasks;

public class Subtask extends Task {

    private Epic epic; //информация об эпике в рамках которого выполняется эта подзадача

    public Subtask(String nameTask, String description, Epic epic) {
        super(nameTask, description);
        this.epic= epic;
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
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
