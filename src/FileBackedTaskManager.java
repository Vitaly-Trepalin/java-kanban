import com.sun.jdi.InvalidLineNumberException;
import exception.ManagerSaveException;
import task.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static task.Status.*;
import static task.Type.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    Path path;

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    public void save() {
        StringBuilder listOfAllTasks = new StringBuilder();
        listOfAllTasks.append("id,type,name,status,description,epic").append("\n");

        for (Task task : super.getTasks().values()) {
            listOfAllTasks.append(task.toString()).append("\n");
        }

        for (Task epic : super.getEpics().values()) {
            listOfAllTasks.append(epic.toString()).append("\n");
        }
        for (Task subtask : super.getSubtasks().values()) {
            listOfAllTasks.append(subtask.toString()).append("\n");
        }

        try {
            Files.writeString(path,
                    listOfAllTasks.toString(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка ввода-вывода");
        }
    }

    public Task fromString(String value) {
        String[] objectFields = value.split(",");

        int id = Integer.getInteger(objectFields[0]);
        Type type = getType(objectFields[1]);
        String name = objectFields[2];
        Status status = getStatus(objectFields[3]);
        String description = objectFields[4];

        if (type.equals(TASK)) {
            return new Task(name, description, id, status);
        } else if (type.equals(EPIC)) {
            return new Epic(name, description, id, status, new ArrayList<>());
        } else {
            return new Subtask(name, description, id, status, new);
        }
    }

    public Type getType(String enumerationElement) {
        if (enumerationElement.equals(TASK.toString())) {
            return TASK;
        } else if (enumerationElement.equals(EPIC.toString())) {
            return EPIC;
        } else if (enumerationElement.equals(SUBTASK.toString()) {
            return SUBTASK;
        }
        throw new RuntimeException("Нет такого типа задачи");
    }

    public Status getStatus(String enumerationElement) {
        if (enumerationElement.equals(NEW.toString())) {
            return NEW;
        } else if (enumerationElement.equals(IN_PROGRESS.toString())) {
            return IN_PROGRESS;
        } else if (enumerationElement.equals(DONE.toString())) {
            return DONE;
        }
        throw new RuntimeException("Нет такого статуса");
    }

    public void deletingTasks() {
        super.deletingTasks();
        save();
    }

    public void deletingEpics() {
        super.deletingEpics();
        save();
    }

    public void deletingSubtask() {
        super.deletingSubtask();
        save();
    }

    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    public void addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
    }

    public void addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
    }

    public void taskUpdate(Task task) {
        super.taskUpdate(task);
        save();
    }

    public void epicUpdate(Epic epic) {
        super.epicUpdate(epic);
        save();
    }

    public void subtaskUpdate(Subtask subtask) {
        super.subtaskUpdate(subtask);
        save();
    }

    public void deleteByIdTask(int id) {
        super.deleteByIdTask(id);
        save();
    }

    public void deleteByIdEpic(int id) {
        super.deleteByIdEpic(id);
        save();
    }

    public void deleteByIdSubtask(int id) {
        super.deleteByIdSubtask(id);
        save();
    }
}
