package tech.goksi.tabbycontrol.events.hooks;

import tech.goksi.tabbycontrol.api.models.events.GenericEvent;

public interface EventListener {

    void onEvent(GenericEvent event);
}
