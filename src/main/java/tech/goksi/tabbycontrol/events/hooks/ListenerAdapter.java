package tech.goksi.tabbycontrol.events.hooks;

import tech.goksi.tabbycontrol.api.models.events.AuthEvent;
import tech.goksi.tabbycontrol.api.models.events.CommandReceiveEvent;
import tech.goksi.tabbycontrol.api.models.events.GenericEvent;

public abstract class ListenerAdapter implements EventListener {

    public abstract void onAuthEvent(AuthEvent event);

    public abstract void onCommandSendEvent(CommandReceiveEvent event);

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof AuthEvent) {
            onAuthEvent((AuthEvent) event);
        } else if (event instanceof CommandReceiveEvent) {
            onCommandSendEvent((CommandReceiveEvent) event);
        }
    }
}
