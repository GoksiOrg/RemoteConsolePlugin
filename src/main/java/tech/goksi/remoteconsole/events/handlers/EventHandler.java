package tech.goksi.remoteconsole.events.handlers;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;

public interface EventHandler {
    void handle(JsonObject jsonObject, WsContext context);
}
