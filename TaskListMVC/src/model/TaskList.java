package model;

import javax.sql.rowset.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList {
    private int id;
    private String title;
    private List<Task> tasks;

    public TaskList() {
    }

    public TaskList(int id, String title) {
        this.id = id;
        this.title = title;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
