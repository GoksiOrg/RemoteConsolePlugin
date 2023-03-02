package tech.goksi.tabbycontrol.api.models.events;

import io.javalin.websocket.WsContext;

import java.util.Collections;

public class AuthEvent extends WebsocketEvent {
    private final String jwt;

    public AuthEvent(String token, WsContext context) {
        super("auth", Collections.singletonList(token), context);
        this.jwt = (String) getData().get(0);
    }

    public String getJWToken() {
        return jwt;
    }
}
