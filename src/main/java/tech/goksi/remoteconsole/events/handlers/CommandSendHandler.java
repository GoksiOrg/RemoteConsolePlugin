package tech.goksi.remoteconsole.events.handlers;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.models.events.CommandSendEvent;

public class CommandSendHandler implements EventHandler {
    @Override
    public void handle(JsonObject jsonObject, WsContext context) {
        RemoteConsole.getInstance().getWebsocketHandler().handleInternal(new CommandSendEvent(jsonObject
                .getAsJsonArray("data")
                .get(0)
                .getAsString(), context));
    }
}
