package tech.goksi.tabbycontrol.api.models.events;

import tech.goksi.tabbycontrol.api.models.ServerInfo;

public class ServerInfoEvent extends GenericEvent {
    public ServerInfoEvent(ServerInfo serverInfo) {
        super("server_info", serverInfo);
    }
}
