package tech.goksi.tabbycontrol.api.exceptions;

public class WsTokenException extends WebSocketException {
    public WsTokenException(String message) {
        super("token_exception", message);
    }

    public WsTokenException(String rawMessage, Object... replacements) {
        super("token_exception", rawMessage, replacements);
    }
}
