package tech.goksi.tabbycontrol.helpers;

import io.javalin.websocket.WsContext;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.utility.ConversionUtility;

import java.util.HashMap;
import java.util.Map;

/*TODO: this is probably bad*/
public class WsAutomaticPing {
    private final Map<WsContext, BukkitTask> pingTasks;
    private final BukkitScheduler scheduler;

    public WsAutomaticPing() {
        pingTasks = new HashMap<>();
        scheduler = Bukkit.getScheduler();
    }

    public void enableAutomaticPings(WsContext context) {
        disableAutomaticPings(context);
        BukkitTask task = scheduler.runTaskTimerAsynchronously(TabbyControl.getInstance(),
                () -> context.sendPing(),
                ConversionUtility.minutesToTicks(1),
                ConversionUtility.minutesToTicks(1));
        pingTasks.put(context, task);
    }

    public void disableAutomaticPings(WsContext context) {
        BukkitTask task = pingTasks.get(context);
        if (task == null) return;
        task.cancel();
        pingTasks.remove(context);
    }
}
