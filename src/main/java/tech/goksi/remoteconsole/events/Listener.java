package tech.goksi.remoteconsole.events;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.exceptions.WebSocketException;
import tech.goksi.remoteconsole.api.models.ConsoleUser;
import tech.goksi.remoteconsole.api.models.events.AuthEvent;
import tech.goksi.remoteconsole.api.models.events.CommandSendEvent;
import tech.goksi.remoteconsole.events.hooks.ListenerAdapter;
import tech.goksi.remoteconsole.token.JWTParser;

public class Listener extends ListenerAdapter {
    /*TODO token expiring, bukkit scheduler ig, and add token expired event*/
    @Override
    public void onAuthEvent(AuthEvent event) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWTParser.parse(event.getJWToken());
        } catch (JWTVerificationException exception) {
            event.getContext().send(new WebSocketException("TokenException", "Provided JWT is invalid"));
            return;
        }
        String username = decodedJWT.getClaim("user").asString();
        RemoteConsole.getInstance()
                .getWebsocketHandler()
                .addObserver(new ConsoleUser(username, event.getContext()));
        event.getContext().send("{\"event\": \"AuthSuccess\"}");
    }

    @Override
    public void onCommandSendEvent(CommandSendEvent event) {
        System.out.println(event.getCommand());
    }
}
