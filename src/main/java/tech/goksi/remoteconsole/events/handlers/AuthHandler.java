package tech.goksi.remoteconsole.events.handlers;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.models.events.AuthEvent;

public class AuthHandler implements EventHandler {

    @Override
    public void handle(JsonObject jsonObject, WsContext context) {
        RemoteConsole.getInstance().getWebsocketHandler().handleInternal(new AuthEvent(jsonObject
                .getAsJsonArray("data")
                .get(0)
                .getAsString(), context));
    }
}
