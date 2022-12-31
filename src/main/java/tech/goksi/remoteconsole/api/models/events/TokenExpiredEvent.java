package tech.goksi.remoteconsole.api.models.events;

public class TokenExpiredEvent extends GenericEvent{
    public TokenExpiredEvent() {
        super("token_expired", null);
    }
}
