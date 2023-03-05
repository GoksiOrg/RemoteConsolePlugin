package tech.goksi.tabbycontrol.api;

import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.exceptions.UnauthorizedException;
import tech.goksi.tabbycontrol.api.rest.controller.ResourceController;
import tech.goksi.tabbycontrol.api.websocket.controller.WebsocketController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    public Routes() {
        Javalin app = TabbyControl.getInstance().getJavalinApp();

        app.before(context -> {
            String token = extractToken(context);
            if (token.isEmpty() || !token.equals(TabbyControl.getInstance().getConfig().getString("ConsoleConfiguration.Secret")))
                throw new UnauthorizedException();
        });

        app.routes(() -> path("api", () -> {
            ws("ws", ws -> {
                ws.onConnect(WebsocketController::onConnect);
                ws.onMessage(WebsocketController::onMessage);
                ws.onClose(WebsocketController::onClose);
            });
            get("resources", ResourceController::get);
        }));
    }

    private String extractToken(Context context) {
        String header = context.header(Header.AUTHORIZATION);
        String[] splitHeader;
        if (header == null || !(splitHeader = header.split("\\s+"))[0].equals("Bearer") || splitHeader.length != 2)
            return "";
        return splitHeader[1];
    }
}
