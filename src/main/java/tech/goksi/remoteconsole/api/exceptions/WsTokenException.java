package tech.goksi.remoteconsole.api.exceptions;

public class WsTokenException extends WebSocketException {
    public WsTokenException(String message) {
        super("token_exception", message);
    }

    public WsTokenException(String rawMessage, Object... replacements) {
        super("token_exception", rawMessage, replacements);
    }
}
