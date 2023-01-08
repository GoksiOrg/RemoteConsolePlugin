package tech.goksi.remoteconsole.api.models.events;

import java.util.Collections;

public class TokenExpiredEvent extends GenericEvent {
    public TokenExpiredEvent() {
        super("token_expired", Collections.emptyList());
    }
}
