package tech.goksi.tabbycontrol.api.rest.controller;

import io.javalin.http.Context;
import org.bukkit.Bukkit;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.api.models.ServerInfo;
import tech.goksi.tabbycontrol.api.models.events.ServerInfoEvent;
import tech.goksi.tabbycontrol.api.websocket.WebsocketHandler;
import tech.goksi.tabbycontrol.utility.ConversionUtility;

public class ResourceController {

    public static void get(Context context) {
        context.json(new ServerInfo());
    }

    public static void initWebsocket(WebsocketHandler handler) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(TabbyControl.getInstance(), () ->
                handler.send(() -> new ServerInfoEvent(new ServerInfo())), 0, ConversionUtility.secondsToTicks(10));
    }
}
