package tech.goksi.tabbycontrol.api.websocket;

import io.javalin.websocket.WsContext;
import tech.goksi.tabbycontrol.api.models.TabbyUser;
import tech.goksi.tabbycontrol.api.models.events.GenericEvent;
import tech.goksi.tabbycontrol.api.rest.controller.ResourceController;
import tech.goksi.tabbycontrol.events.Listener;
import tech.goksi.tabbycontrol.events.handlers.AuthHandler;
import tech.goksi.tabbycontrol.events.handlers.CommandSendHandler;
import tech.goksi.tabbycontrol.events.handlers.EventHandler;
import tech.goksi.tabbycontrol.events.hooks.EventListener;
import tech.goksi.tabbycontrol.helpers.WsAutomaticPing;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public class WebsocketHandler {
    public static final Date STARTUP_TIME;
    private final List<TabbyUser> observers;
    private final Map<String, EventHandler> handlers;
    private final List<EventListener> listeners;
    private final WsAutomaticPing automaticPingHandler;

    static {
        STARTUP_TIME = Calendar.getInstance().getTime();
    }

    public WebsocketHandler() {
        observers = new CopyOnWriteArrayList<>();
        handlers = new HashMap<>();
        listeners = new ArrayList<>();
        setupHandlers();
        addListener(new Listener());
        automaticPingHandler = new WsAutomaticPing();
        ResourceController.initWebsocket(this);
    }

    public void addObserver(TabbyUser observer) {
        observers.add(observer);
        automaticPingHandler.enableAutomaticPings(observer.getContext());
    }

    public void removeObserver(WsContext context) {
        observers.removeIf(tabbyUser -> tabbyUser.getContext().equals(context));
        automaticPingHandler.disableAutomaticPings(context);
    }

    public void removeObserver(TabbyUser tabbyUser) {
        observers.remove(tabbyUser);
        automaticPingHandler.disableAutomaticPings(tabbyUser.getContext());
    }

    public TabbyUser getObserver(WsContext context) {
        return observers.stream().filter(tabbyUser -> tabbyUser.getContext().equals(context)).findFirst().orElse(null);
    }

    public void send(GenericEvent event) {
        Iterator<TabbyUser> iterator = observers.listIterator();
        while (iterator.hasNext()) {
            TabbyUser tabbyUser = iterator.next();
            if (tabbyUser.getContext().session.isOpen()) {
                tabbyUser.getContext().send(event);
            } else {
                iterator.remove();
            }
        }
    }

    public void send(Supplier<GenericEvent> eventSupplier) {
        if (observers.isEmpty()) return;
        send(eventSupplier.get());
    }

    public void handleInternal(GenericEvent event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    private void setupHandlers() {
        handlers.put("auth", new AuthHandler());
        handlers.put("command_send", new CommandSendHandler());
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public Map<String, EventHandler> getHandlers() {
        return handlers;
    }
}
