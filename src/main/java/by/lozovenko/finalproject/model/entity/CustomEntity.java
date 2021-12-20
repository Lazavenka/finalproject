package by.lozovenko.finalproject.model.entity;

import java.io.Serializable;

public abstract class CustomEntity implements Serializable, Cloneable {
    private long id;

    public CustomEntity() {
    }

    public CustomEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
