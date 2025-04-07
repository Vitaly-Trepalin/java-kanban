package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static task.Type.EPIC;

public class Epic extends Task {
    private List<Subtask> subtasks;
    private LocalDateTime endTime;

    public Epic(String nameEpic, String description) {
        super(nameEpic, description, null, 0, null);
        subtasks = new ArrayList<>();
        super.setStatus(setStatusForEpic());
        super.setDuration(setDurationForEpics());
        super.setStartTime(setStartTimeForEpics());
        endTime = setEndTimeForEpics();
        super.setType(EPIC);
    }

    public Epic(String nameEpic, String description, int id) {
        super(nameEpic, description, id, null, 0, null);
        subtasks = new ArrayList<>();
        super.setStatus(setStatusForEpic());
        super.setDuration(setDurationForEpics());
        super.setStartTime(setStartTimeForEpics());
        endTime = setEndTimeForEpics();
        super.setType(EPIC);
    }

    public Epic(String nameEpic, String description, int id, List<Subtask> subtasks) {
        super(nameEpic, description, id, null, 0, null);
        this.subtasks = subtasks;
        super.setStatus(setStatusForEpic());
        super.setDuration(setDurationForEpics());
        super.setStartTime(setStartTimeForEpics());
        endTime = setEndTimeForEpics();
        super.setType(EPIC);
    }

    public Epic(String nameEpic, String description, int id, Status status, long duration,
                LocalDateTime localDateTime) {
        super(nameEpic, description, id, status, duration, localDateTime);
        subtasks = new ArrayList<>();
        endTime = setEndTimeForEpics();
        super.setType(EPIC);
    }


    public Status setStatusForEpic() {
        if (subtasks.isEmpty()) {
            return Status.NEW;
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
            return Status.NEW;
        } else if (numberOfSubtasksWithStatusDone == subtasks.size()) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public long setDurationForEpics() {
        return subtasks.stream()
                .map(Task::getDuration)
                .reduce(Duration.ZERO, Duration::plus)
                .toMinutes();
    }

    public LocalDateTime setStartTimeForEpics() {
        Optional<LocalDateTime> startTime = subtasks.stream()
                .map(Task::getStartTime)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);
        return startTime.orElseGet(() -> null);
    }

    public LocalDateTime setEndTimeForEpics() {
        Optional<LocalDateTime> startOfTheLastTask = subtasks.stream()
                .map(Task::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo);
        LocalDateTime endTime = startOfTheLastTask.orElseGet(() -> null);
        return endTime != null ? endTime.plus(getDuration()) : null;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
}
