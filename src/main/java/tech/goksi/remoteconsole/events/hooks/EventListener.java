package tech.goksi.remoteconsole.events.hooks;

import tech.goksi.remoteconsole.api.models.events.GenericEvent;

public interface EventListener {

    void onEvent(GenericEvent event);
}
