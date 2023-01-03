package tech.goksi.remoteconsole.api.models;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.websocket.WsContext;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tech.goksi.remoteconsole.RemoteConsole;
import tech.goksi.remoteconsole.api.models.events.TokenExpiredEvent;
import tech.goksi.remoteconsole.api.models.events.TokenExpiringEvent;
import tech.goksi.remoteconsole.utility.ConversionUtility;

import java.util.Objects;
/*TODO add check for name again after setting new jwt*/
public class ConsoleUser {
    private final String name;
    private final WsContext context;
    private DecodedJWT jwt;
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
        checkerTask = Bukkit.getScheduler().runTaskTimerAsynchronously(RemoteConsole.getInstance(), () -> {
            long difference = jwt.getExpiresAt().getTime() - System.currentTimeMillis();
            if (difference > 0) {
                context.send(new TokenExpiringEvent(ConversionUtility.millisToMinutes(difference)));
            } else {
                context.send(new TokenExpiredEvent());
                RemoteConsole.getInstance().getWebsocketHandler().removeObserver(this);
                checkerTask.cancel();
            }
        }, ConversionUtility.minutesToTicks(25), ConversionUtility.minutesToTicks(3));
    }

    public void cancelCheck() {
        if (checkerTask != null && !checkerTask.isCancelled()) {
            checkerTask.cancel();
        }
    }

    public void setJwt(DecodedJWT jwt) {
        this.jwt = jwt;
        cancelCheck();
        runCheck(); // make it wait 25 minutes again
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsoleUser that = (ConsoleUser) o;
        return context.equals(that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }
}
