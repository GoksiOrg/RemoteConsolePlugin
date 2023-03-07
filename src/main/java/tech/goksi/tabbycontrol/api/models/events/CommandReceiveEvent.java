package tech.goksi.tabbycontrol.api.models.events;

import io.javalin.websocket.WsContext;

import java.util.Collections;

public class CommandReceiveEvent extends WebsocketEvent {
    private final String command;

    public CommandReceiveEvent(String command, WsContext context) {
        super("command_send", Collections.singletonMap("command", command), context);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
