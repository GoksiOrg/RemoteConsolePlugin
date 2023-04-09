package tech.goksi.tabbycontrol.events;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bukkit.Bukkit;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.exceptions.WsTokenException;
import tech.goksi.tabbycontrol.api.models.TabbyUser;
import tech.goksi.tabbycontrol.api.models.events.AuthEvent;
import tech.goksi.tabbycontrol.api.models.events.CommandReceiveEvent;
import tech.goksi.tabbycontrol.api.websocket.WebsocketHandler;
import tech.goksi.tabbycontrol.events.hooks.ListenerAdapter;
import tech.goksi.tabbycontrol.token.JWTParser;

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
        TabbyUser tabbyUser = TabbyControl.getInstance().getWebsocketHandler().getObserver(event.getContext());
        if (tabbyUser != null) {
            tabbyUser.setJwt(decodedJWT);
            return;
        }
        tabbyUser = new TabbyUser(decodedJWT, event.getContext());
        TabbyControl.getInstance()
                .getWebsocketHandler()
                .addObserver(tabbyUser);
        /*would like to send anonymous instance of GenericEvent here, but unfortunately gson doesn't support that*/
        event.getContext().send("{\"event\": \"auth_success\", \"data\": []}");
        tabbyUser.runCheck();
    }

    @Override
    public void onCommandSendEvent(CommandReceiveEvent event) {
        TabbyUser tabbyUser = TabbyControl.getInstance().getWebsocketHandler()
                .getObserver(event.getContext());
        if (!tabbyUser.canRunCommands()) {
            // some logic
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getCommand());
    }
}
