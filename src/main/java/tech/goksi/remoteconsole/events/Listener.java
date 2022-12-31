package tech.goksi.remoteconsole.events;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.exceptions.WebSocketException;
import tech.goksi.remoteconsole.api.models.ConsoleUser;
import tech.goksi.remoteconsole.api.models.events.AuthEvent;
import tech.goksi.remoteconsole.api.models.events.CommandSendEvent;
import tech.goksi.remoteconsole.api.websocket.WebsocketHandler;
import tech.goksi.remoteconsole.events.hooks.ListenerAdapter;
import tech.goksi.remoteconsole.token.JWTParser;

public class Listener extends ListenerAdapter {
    @Override
    public void onAuthEvent(AuthEvent event) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWTParser.parse(event.getJWToken());
        } catch (JWTVerificationException exception) {
            event.getContext().send(new WebSocketException("TokenException", "Provided JWT is invalid"));
            return;
        }
        if (decodedJWT.getIssuedAt().before(WebsocketHandler.STARTUP_TIME)) {
            event.getContext().send(new WebSocketException("TokenException", "Mismatched JWT time"));
            return;
        }
        ConsoleUser consoleUser = new ConsoleUser(decodedJWT, event.getContext());
        RemoteConsole.getInstance()
                .getWebsocketHandler()
                .addObserver(consoleUser);
        event.getContext().send("{\"event\": \"AuthSuccess\"}");
        consoleUser.runCheck();
    }

    @Override
    public void onCommandSendEvent(CommandSendEvent event) {
        System.out.println(event.getCommand());
    }
}
