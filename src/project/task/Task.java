package project.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static project.task.Type.TASK;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Type type = TASK;
    private Duration duration;
    private LocalDateTime startTime;

    public Task() {}

    public Task(String nameTask, String description, Status status, long duration, LocalDateTime startTime) {
        this.name = nameTask;
        this.description = description;
        this.status = status;
        setDuration(duration);
        this.startTime = startTime;
    }

    public Task(String name, String description, int id, Status status, long duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        setDuration(duration);
        this.startTime = startTime;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = Duration.ofMinutes(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
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
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration.toMinutes() + ","
                + startTime;
    }
}