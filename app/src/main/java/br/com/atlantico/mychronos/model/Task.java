package br.com.atlantico.mychronos.model;

/**
 * Created by pereira_ygor on 19/06/2015.
 */
public class Task {

    private long id;
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public Task(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
