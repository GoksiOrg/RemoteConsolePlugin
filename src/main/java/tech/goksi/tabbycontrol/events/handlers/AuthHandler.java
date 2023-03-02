package tech.goksi.tabbycontrol.events.handlers;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.models.events.AuthEvent;

public class AuthHandler implements EventHandler {

    @Override
    public void handle(JsonObject jsonObject, WsContext context) {
        TabbyControl.getInstance().getWebsocketHandler().handleInternal(new AuthEvent(jsonObject
                .getAsJsonArray("data")
                .get(0)
                .getAsString(), context));
    }
}
