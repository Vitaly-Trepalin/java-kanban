package main.task;

import java.util.Objects;

public class Task {
    private String name;
    private String Description;
    private int id;
    private Status status;

    public Task(String nameTask, String description) {
        this.name = nameTask;
        Description = description;
    }

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        Description = description;
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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(Description, task.Description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Description, id, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}