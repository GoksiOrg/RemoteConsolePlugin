package tech.goksi.remoteconsole.api.models.events;

import java.util.Collections;

public class AuthEvent extends GenericEvent{
    public AuthEvent(String name, String token) {
        super(name, Collections.singletonList(token));
    }

    public String getJWToken() {
        return (String) getData().get(0);
    }
}
