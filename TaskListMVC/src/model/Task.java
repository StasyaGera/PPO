package model;

public class Task {
    private int id;
    private boolean done;
    private String text;

    public Task(String text) {
        this.done = false;
        this.text = text;
    }

    public void setDone() {
        done = true;
    }

    public boolean getDone() {
        return done;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
