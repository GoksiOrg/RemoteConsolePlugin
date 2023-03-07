package tech.goksi.tabbycontrol.api.models.events;

import com.google.gson.annotations.Expose;
import tech.goksi.tabbycontrol.api.models.abs.EventModel;

import java.util.Map;

public abstract class GenericEvent {
    @Expose
    private final String event;
    @Expose
    private final Map<String, Object> data;

    public GenericEvent(String name, Map<String, Object> data) {
        this.event = name;
        this.data = data;
    }

    public GenericEvent(String name, EventModel model) {
        this(name, model.toEventMap());
    }

    public String getName() {
        return event;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
