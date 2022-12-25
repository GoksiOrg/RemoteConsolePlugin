package tech.goksi.remoteconsole.events.hooks;

import tech.goksi.remoteconsole.api.models.events.AuthEvent;
import tech.goksi.remoteconsole.api.models.events.CommandSendEvent;
import tech.goksi.remoteconsole.api.models.events.GenericEvent;

public abstract class ListenerAdapter implements EventListener{

    public abstract void onAuthEvent(AuthEvent event);
    public abstract void onCommandSendEvent(CommandSendEvent event);
    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof AuthEvent) {
            onAuthEvent((AuthEvent) event);
        } else if (event instanceof CommandSendEvent) {
            onCommandSendEvent((CommandSendEvent) event);
        }
    }
}
