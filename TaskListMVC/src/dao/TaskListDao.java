package dao;

import model.Task;
import model.TaskList;

import java.util.List;

public interface TaskListDao {
    int addList(TaskList list);
    int removeList(TaskList list);

    int addTask(Task list);
    int removeTask(Task list);

    List<TaskList> getLists();
    List<Task> getTasks();
}
