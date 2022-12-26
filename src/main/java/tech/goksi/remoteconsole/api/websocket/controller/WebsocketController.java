package tech.goksi.remoteconsole.api.websocket.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.exceptions.WebSocketException;
import tech.goksi.remoteconsole.events.handlers.EventHandler;
import tech.goksi.remoteconsole.helpers.GsonSingleton;

public class WebsocketController {
    private static final RemoteConsole plugin = RemoteConsole.getInstance();

    private WebsocketController() {
    }

    public static void onConnect(WsContext context) {
        context.send("{\"status\": \"Awaiting auth event\"}");
    }

    public static void onMessage(WsContext context) {
        JsonObject eventObject;
        try {
            eventObject = GsonSingleton.getInstance().fromJson(((WsMessageContext) context).message(), JsonObject.class);
        } catch (JsonSyntaxException exception) {
            context.send(new WebSocketException("InvalidInput", "Provided input is not an valid json string"));
            return;
        }
        JsonElement eventNameElement = eventObject.get("event");
        if (eventNameElement == null) {
            context.send(new WebSocketException("InvalidInput", "Invalid format, event field missing"));
            return;
        }
        JsonElement dataElement = eventObject.get("data");
        if(dataElement == null || !dataElement.isJsonArray()) {
            context.send(new WebSocketException("InvalidInput", "Invalid format, data field must be array and not null"));
            return;
        }
        EventHandler handler = plugin.getWebsocketHandler().getHandlers().get(eventNameElement.getAsString());
        if (handler == null) {
            context.send(new WebSocketException("InvalidInput", "Unknown event \"%s\"", eventNameElement.getAsString()));
            return;
        }
        handler.handle(eventObject, context);
    }

    public static void onClose(WsContext context) {
        plugin.getWebsocketHandler().removeObserver(context);
    }
}
