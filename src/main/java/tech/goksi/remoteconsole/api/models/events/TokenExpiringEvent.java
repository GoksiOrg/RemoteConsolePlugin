package tech.goksi.remoteconsole.api.models.events;

import java.util.Collections;

public class TokenExpiringEvent extends GenericEvent{
    public TokenExpiringEvent(int expiresIn) {
        super("token_expiring", Collections.singletonList(expiresIn));
    }
}
