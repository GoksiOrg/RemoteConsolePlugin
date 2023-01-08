package tech.goksi.remoteconsole.api.websocket.controller;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.exceptions.WsValidationException;
import tech.goksi.remoteconsole.api.models.ConsoleUser;
import tech.goksi.remoteconsole.events.handlers.EventHandler;
import tech.goksi.remoteconsole.helpers.EventValidator;

public class WebsocketController {
    private static final RemoteConsole plugin = RemoteConsole.getInstance();

    private WebsocketController() {
    }

    public static void onConnect(WsContext context) {
        context.send("{\"status\": \"Awaiting auth event\"}");
    }

    public static void onMessage(WsContext context) {
        JsonObject eventObject = EventValidator.validate(((WsMessageContext) context).message());
        EventHandler handler = plugin.getWebsocketHandler().getHandlers().get(eventObject.get("event").getAsString());
        if (handler == null) {
            throw new WsValidationException("Unknown event %s", eventObject.get("event").getAsString());
        }
        handler.handle(eventObject, context);
    }

    public static void onClose(WsContext context) {
        ConsoleUser observer = plugin.getWebsocketHandler().getObserver(context);
        if (observer != null) {
            observer.cancelCheck();
            plugin.getWebsocketHandler().removeObserver(observer);
        }
    }
}
