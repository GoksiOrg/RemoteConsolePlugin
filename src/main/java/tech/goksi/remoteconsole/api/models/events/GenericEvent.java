package tech.goksi.remoteconsole.api.models.events;

import java.util.List;

public abstract class GenericEvent {
    private final String event;
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
