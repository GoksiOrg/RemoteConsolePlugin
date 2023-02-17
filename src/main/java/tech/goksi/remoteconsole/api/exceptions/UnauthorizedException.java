package tech.goksi.remoteconsole.api.exceptions;

public class UnauthorizedException extends RestException {


    public UnauthorizedException() {
        super(401, "Unauthorized", "You are not authorized to access this endpoint !");
    }
}
