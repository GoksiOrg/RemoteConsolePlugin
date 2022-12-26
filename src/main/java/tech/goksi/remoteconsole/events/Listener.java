package tech.goksi.remoteconsole.events;

import tech.goksi.remoteconsole.api.models.events.AuthEvent;
import tech.goksi.remoteconsole.api.models.events.CommandSendEvent;
import tech.goksi.remoteconsole.events.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    @Override
    public void onAuthEvent(AuthEvent event) {
        System.out.println(event.getJWToken());
    }

    @Override
    public void onCommandSendEvent(CommandSendEvent event) {
        System.out.println(event.getCommand());
    }
}
