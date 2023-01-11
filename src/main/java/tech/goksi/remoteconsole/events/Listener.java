package tech.goksi.remoteconsole.events;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.exceptions.WsTokenException;
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
            throw new WsTokenException("Provided JWT is invalid");
        }
        if (decodedJWT.getIssuedAt().before(WebsocketHandler.STARTUP_TIME))
            throw new WsTokenException("Mismatched JWT time");
        ConsoleUser consoleUser = RemoteConsole.getInstance().getWebsocketHandler().getObserver(event.getContext());
        if (consoleUser != null) {
            consoleUser.setJwt(decodedJWT);
            return;
        }
        consoleUser = new ConsoleUser(decodedJWT, event.getContext());
        RemoteConsole.getInstance()
                .getWebsocketHandler()
                .addObserver(consoleUser);
        /*would like to send anonymous instance of GenericEvent here, but unfortunately gson doesn't support that*/
        event.getContext().send("{\"event\": \"auth_success\", \"data\": []}");
        consoleUser.runCheck();
    }

    @Override
    public void onCommandSendEvent(CommandSendEvent event) {
        System.out.println(event.getCommand());
    }
}
