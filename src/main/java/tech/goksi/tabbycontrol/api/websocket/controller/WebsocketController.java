package tech.goksi.tabbycontrol.api.websocket.controller;

import com.google.gson.JsonObject;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.exceptions.WsValidationException;
import tech.goksi.tabbycontrol.api.models.ConsoleUser;
import tech.goksi.tabbycontrol.events.handlers.EventHandler;
import tech.goksi.tabbycontrol.helpers.EventValidator;

public class WebsocketController {
    private static final TabbyControl plugin = TabbyControl.getInstance();

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
