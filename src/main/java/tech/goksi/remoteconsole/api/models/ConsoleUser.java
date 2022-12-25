package tech.goksi.remoteconsole.api.models;

import io.javalin.websocket.WsContext;

public class ConsoleUser {
    private final String name;
    private final WsContext context;

    public ConsoleUser(String name, WsContext context) {
        this.context = context;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public WsContext getContext() {
        return context;
    }
}
