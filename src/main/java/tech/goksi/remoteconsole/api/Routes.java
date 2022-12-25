package tech.goksi.remoteconsole.api;

import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.websocket.controller.WebsocketController;

import static io.javalin.apibuilder.ApiBuilder.*;
public class Routes {
    public Routes() {

        RemoteConsole.getInstance().getJavalinApp().routes(() -> path("api", () -> {
            ws("ws", ws -> {
                ws.onConnect(WebsocketController::onConnect);
                ws.onMessage(WebsocketController::onMessage);
                ws.onClose(WebsocketController::onClose);
            });
        }));
    }
}
