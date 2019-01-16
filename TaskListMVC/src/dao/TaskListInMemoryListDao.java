package dao;

import model.Product;
import model.Task;
import model.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskListInMemoryListDao implements TaskListDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<TaskList> tasklists = new CopyOnWriteArrayList<>();

    @Override
    public int addList(TaskList list) {
        tasklists.add(list);
    }

    @Override
    public int removeList(TaskList list) {
        return 0;
    }

    @Override
    public int addTask(Task list) {
        return 0;
    }

    @Override
    public int removeTask(Task list) {
        return 0;
    }

    @Override
    public List<TaskList> getLists() {
        return null;
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }
}
