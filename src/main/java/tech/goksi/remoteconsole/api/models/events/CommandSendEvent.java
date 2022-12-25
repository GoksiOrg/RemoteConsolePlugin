package tech.goksi.remoteconsole.api.models.events;

import java.util.Collections;

public class CommandSendEvent extends GenericEvent{
    public CommandSendEvent(String name, String command) {
        super(name, Collections.singletonList(command));
    }

    public String getCommand() {
        return (String) getData().get(0);
    }
}
