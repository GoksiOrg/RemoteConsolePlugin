package tech.goksi.remoteconsole.api.websocket.controller;

import io.javalin.websocket.WsContext;
import tech.goksi.remoteconsole.RemoteConsole;

public class WebsocketController {
    private static final RemoteConsole plugin = RemoteConsole.getInstance();
    private WebsocketController() {
    }

    public static void onConnect(WsContext context) {
        context.send("{\"status\": \"Awaiting auth event\"}");
    }

    public static void onMessage(WsContext context) {

    }

    public static void onClose(WsContext context) {
        plugin.getWebsocketHandler().removeObserver(context);
    }
}
