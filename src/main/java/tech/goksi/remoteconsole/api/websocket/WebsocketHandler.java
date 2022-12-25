package tech.goksi.remoteconsole.api.websocket;

import io.javalin.websocket.WsContext;
import tech.goksi.remoteconsole.api.models.ConsoleUser;
import tech.goksi.remoteconsole.api.models.events.GenericEvent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*TODO auth event, after that add to observers*/
public class WebsocketHandler {
    private final List<ConsoleUser> observers;

    public WebsocketHandler() {
        observers = new CopyOnWriteArrayList<>();
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

    public void handle(GenericEvent event) {

    }
}
