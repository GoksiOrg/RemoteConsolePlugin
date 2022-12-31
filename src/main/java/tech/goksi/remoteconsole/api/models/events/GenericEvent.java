package tech.goksi.remoteconsole.api.models.events;

import com.google.gson.annotations.Expose;

import java.util.List;

public abstract class GenericEvent {
    @Expose
    private final String event;
    @Expose
    private final List<Object> data;

    public GenericEvent(String name, List<Object> data) {
        this.event = name;
        this.data = data;
    }

    public String getName() {
        return event;
    }

    public List<Object> getData() {
        return data;
    }
}
