package tech.goksi.tabbycontrol.api.models.events;

import tech.goksi.tabbycontrol.api.models.ServerInfo;

import java.util.Arrays;

public class ServerInfoEvent extends GenericEvent {
    public ServerInfoEvent(ServerInfo serverInfo) {
        super("server_info", Arrays.asList(
                serverInfo.getTotalRam(),
                serverInfo.getUsedRam(),
                serverInfo.getCpuUsage(),
                serverInfo.getOnlinePlayers(),
                serverInfo.getMaxPlayers()
        ));
    }
}
