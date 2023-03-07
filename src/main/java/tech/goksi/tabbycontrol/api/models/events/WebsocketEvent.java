package tech.goksi.tabbycontrol.api.models.events;

import io.javalin.websocket.WsContext;

import java.util.Map;

public abstract class WebsocketEvent extends GenericEvent {
    private final WsContext context;

    public WebsocketEvent(String name, Map<String, Object> data, WsContext context) {
        super(name, data);
        this.context = context;
    }

    public WsContext getContext() {
        return context;
    }
}
