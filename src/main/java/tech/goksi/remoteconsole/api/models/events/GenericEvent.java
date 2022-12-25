package tech.goksi.remoteconsole.api.models.events;

import java.util.List;

public abstract class GenericEvent {
    private final String name;
    private final List<Object> data;

    public GenericEvent(String name, List<Object> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public List<Object> getData() {
        return data;
    }
}
