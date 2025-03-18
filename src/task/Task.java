package task;

import java.util.Objects;
import static task.Type.TASK;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Type type = TASK;

    public Task(String nameTask, String description, Status status) {
        this.name = nameTask;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getNameTask() {
        return name;
    }

    public void setNameTask(String nameTask) {
        this.name = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + ",";
    }
}