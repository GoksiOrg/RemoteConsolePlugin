package tech.goksi.tabbycontrol.api.models.events;

import io.javalin.websocket.WsContext;

import java.util.Collections;

public class AuthEvent extends WebsocketEvent {
    private final String jwt;

    public AuthEvent(String token, WsContext context) {
        super("auth", Collections.singletonMap("jwt", token), context);
        this.jwt = token;
    }

    public String getJWToken() {
        return jwt;
    }
}
