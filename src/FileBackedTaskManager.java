import exception.ManagerSaveException;
import task.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static task.Status.*;
import static task.Type.*;


public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
        StringBuilder listOfAllTasks = new StringBuilder();
        listOfAllTasks.append("id,type,name,status,description,epic").append("\n");

        for (Task task : getTasks().values()) {
            listOfAllTasks.append(task.toString()).append("\n");
        }

        for (Task epic : getEpics().values()) {
            listOfAllTasks.append(epic.toString()).append("\n");
        }
        for (Task subtask : getSubtasks().values()) {
            listOfAllTasks.append(subtask.toString()).append("\n");
        }

        try {
            Files.writeString(file.toPath(),
                    listOfAllTasks.toString(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка ввода-вывода");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try {
            String data = Files.readString(file.toPath());
            String[] objects = data.split("\n");
            int id = 0;

            for (int i = 1; i < objects.length; i++) { //заполнение данными хэш-таблиц tasks, epics, subtasks класса
                // FileBackedTaskManager
                Task task = fromString(objects[i]);
                if (id < task.getId()) { //присвоение значения полю id класса FileBackedTaskManager
                    id = task.getId();
                    fileBackedTaskManager.setId(task.getId());
                }
                if (task instanceof Epic epic) {
                    HashMap<Integer, Epic> newEpics = fileBackedTaskManager.getEpics();
                    newEpics.put(epic.getId(), epic);
                    fileBackedTaskManager.setEpics(newEpics);
                } else if (task instanceof Subtask subtask) {
                    HashMap<Integer, Subtask> newSubtasks = fileBackedTaskManager.getSubtasks();
                    newSubtasks.put(subtask.getId(), subtask);
                    fileBackedTaskManager.setSubtasks(newSubtasks);
                } else {
                    HashMap<Integer, Task> newTasks = fileBackedTaskManager.getTasks();
                    newTasks.put(task.getId(), task);
                    fileBackedTaskManager.setTasks(newTasks);
                }
            }

            HashMap<Integer, Epic> newEpics = fileBackedTaskManager.getEpics();
            for (Subtask subtask : fileBackedTaskManager.getSubtasks().values()) { //добавление в эпики списка входящих в них подзадач
                Epic epic = newEpics.get(subtask.getEpicId());
                List<Subtask> subtaskList = epic.getSubtasks();
                subtaskList.add(subtask);
                fileBackedTaskManager.setEpics(newEpics);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBackedTaskManager;
    }

    public static Task fromString(String value) {
        String[] objectFields = value.split(",", 6); //получение объектов Task из строки

        int id = Integer.parseInt(objectFields[0]);
        Type type = getType(objectFields[1]);
        String name = objectFields[2];
        Status status = getStatus(objectFields[3]);
        String description = objectFields[4];
        int epicId = 0;
        if (type.equals(SUBTASK)) {
            epicId = Integer.parseInt(objectFields[5]);
        }

        if (type.equals(TASK)) {
            return new Task(name, description, id, status);
        } else if (type.equals(EPIC)) {
            return new Epic(name, description, id, status, new ArrayList<>());
        } else if (type.equals(SUBTASK)) {
            return new Subtask(name, description, id, status, epicId);
        } else {
            System.out.println("Нет такого типа задачи");
        }
        return null;
    }

    public static Type getType(String enumerationElement) {
        if (enumerationElement.equals(TASK.toString())) { //получение типа из строки
            return TASK;
        } else if (enumerationElement.equals(EPIC.toString())) {
            return EPIC;
        } else if (enumerationElement.equals(SUBTASK.toString())) {
            return SUBTASK;
        }
        throw new RuntimeException("Нет такого типа задачи");
    }

    public static Status getStatus(String enumerationElement) {
        if (enumerationElement.equals(NEW.toString())) { //получение статуса из строки
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
