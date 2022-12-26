package tech.goksi.remoteconsole.api.models.events;

import io.javalin.websocket.WsContext;

import java.util.Collections;

public class CommandSendEvent extends WebsocketEvent {
    private final String command;

    public CommandSendEvent(String command, WsContext context) {
        super("command_send", Collections.singletonList(command), context);
        this.command = (String) getData().get(0);
    }

    public String getCommand() {
        return command;
    }
}
