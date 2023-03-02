package tech.goksi.tabbycontrol.api.websocket;

import io.javalin.websocket.WsContext;
import tech.goksi.tabbycontrol.api.models.ConsoleUser;
import tech.goksi.tabbycontrol.api.models.events.GenericEvent;
import tech.goksi.tabbycontrol.events.Listener;
import tech.goksi.tabbycontrol.events.handlers.AuthHandler;
import tech.goksi.tabbycontrol.events.handlers.CommandSendHandler;
import tech.goksi.tabbycontrol.events.handlers.EventHandler;
import tech.goksi.tabbycontrol.events.hooks.EventListener;
import tech.goksi.tabbycontrol.helpers.WsAutomaticPing;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebsocketHandler {
    public static final Date STARTUP_TIME;
    private final List<ConsoleUser> observers;
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
    }

    public void addObserver(ConsoleUser observer) {
        observers.add(observer);
        automaticPingHandler.enableAutomaticPings(observer.getContext());
    }

    public void removeObserver(WsContext context) {
        observers.removeIf(consoleUser -> consoleUser.getContext().equals(context));
        automaticPingHandler.disableAutomaticPings(context);
    }

    public void removeObserver(ConsoleUser consoleUser) {
        observers.remove(consoleUser);
        automaticPingHandler.disableAutomaticPings(consoleUser.getContext());
    }

    public ConsoleUser getObserver(WsContext context) {
        return observers.stream().filter(consoleUser -> consoleUser.getContext().equals(context)).findFirst().orElse(null);
    }

    public void send(GenericEvent event) {
        Iterator<ConsoleUser> iterator = observers.listIterator();
        while (iterator.hasNext()) {
            ConsoleUser consoleUser = iterator.next();
            if (consoleUser.getContext().session.isOpen()) {
                consoleUser.getContext().send(event);
            } else {
                iterator.remove();
            }
        }
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
