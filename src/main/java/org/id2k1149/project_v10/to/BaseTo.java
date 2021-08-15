package org.id2k1149.project_v10.to;

public class BaseTo implements HasId {
    protected Long id;

    public BaseTo(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}