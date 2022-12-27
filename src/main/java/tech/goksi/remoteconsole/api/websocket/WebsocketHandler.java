package tech.goksi.remoteconsole.api.websocket;

import io.javalin.websocket.WsContext;
import tech.goksi.remoteconsole.api.models.ConsoleUser;
import tech.goksi.remoteconsole.api.models.events.GenericEvent;
import tech.goksi.remoteconsole.events.Listener;
import tech.goksi.remoteconsole.events.handlers.AuthHandler;
import tech.goksi.remoteconsole.events.handlers.CommandSendHandler;
import tech.goksi.remoteconsole.events.handlers.EventHandler;
import tech.goksi.remoteconsole.events.hooks.EventListener;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebsocketHandler {
    private final List<ConsoleUser> observers;
    private final Map<String, EventHandler> handlers;
    private final List<EventListener> listeners;


    public WebsocketHandler() {
        observers = new CopyOnWriteArrayList<>();
        handlers = new HashMap<>();
        listeners = new ArrayList<>();
        setupHandlers();
        addListener(new Listener());
    }

    public void addObserver(ConsoleUser observer) {
        observers.add(observer);
    }

    public void removeObserver(WsContext context) {
        observers.removeIf(consoleUser -> consoleUser.getContext().equals(context));
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
