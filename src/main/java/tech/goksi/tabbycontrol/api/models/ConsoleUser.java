package tech.goksi.tabbycontrol.api.models;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.websocket.WsContext;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.exceptions.WsTokenException;
import tech.goksi.tabbycontrol.api.models.events.TokenExpiredEvent;
import tech.goksi.tabbycontrol.api.models.events.TokenExpiringEvent;
import tech.goksi.tabbycontrol.utility.ConversionUtility;

import java.util.Objects;

public class ConsoleUser {
    private final String name;
    private final WsContext context;
    private DecodedJWT jwt;
    private BukkitTask checkerTask;

    public ConsoleUser(DecodedJWT jwt, WsContext context) {
        this.context = context;
        this.jwt = jwt;
        this.name = jwt.getSubject();
    }

    public String getName() {
        return name;
    }

    public WsContext getContext() {
        return context;
    }

    /*TODO: probably make one task for all users*/
    public void runCheck() {
        checkerTask = Bukkit.getScheduler().runTaskTimerAsynchronously(TabbyControl.getInstance(), () -> {
            long difference = jwt.getExpiresAt().getTime() - System.currentTimeMillis();
            if (difference > 0) {
                context.send(new TokenExpiringEvent(ConversionUtility.millisToMinutes(difference)));
            } else {
                context.send(new TokenExpiredEvent());
                TabbyControl.getInstance().getWebsocketHandler().removeObserver(this);
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
        if (!name.equals(jwt.getSubject())) {
            throw new WsTokenException("Provided JWT doesn't match current session");
        }
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
