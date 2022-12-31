package tech.goksi.remoteconsole.api.models;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.websocket.WsContext;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.models.events.TokenExpiredEvent;
import tech.goksi.remoteconsole.api.models.events.TokenExpiringEvent;
import tech.goksi.remoteconsole.utility.ConversionUtility;

public class ConsoleUser {
    private final String name;
    private final WsContext context;
    private final DecodedJWT jwt;
    private BukkitTask checkerTask;

    public ConsoleUser(DecodedJWT jwt, WsContext context) {
        this.context = context;
        this.jwt = jwt;
        this.name = jwt.getClaim("user").asString();
    }

    public String getName() {
        return name;
    }

    public WsContext getContext() {
        return context;
    }

    public void runCheck() {
        checkerTask = Bukkit.getScheduler().runTaskTimer(RemoteConsole.getInstance(), () -> {
            long difference = jwt.getExpiresAt().getTime() - System.currentTimeMillis();
            if (difference > 0) {
                context.send(new TokenExpiringEvent(ConversionUtility.millisToMinutes(difference)));
            } else {
                context.send(new TokenExpiredEvent());
                RemoteConsole.getInstance().getWebsocketHandler().removeObserver(context);
                checkerTask.cancel();
            }
        }, ConversionUtility.minutesToTicks(25), ConversionUtility.minutesToTicks(3));
    }
}
