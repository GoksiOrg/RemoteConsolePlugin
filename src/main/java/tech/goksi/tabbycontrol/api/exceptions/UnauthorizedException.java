package tech.goksi.tabbycontrol.api.exceptions;

public class UnauthorizedException extends RestException {


    public UnauthorizedException() {
        super(401, "Unauthorized", "You are not authorized to access this endpoint !");
    }
}
