package tech.goksi.tabbycontrol.api.exceptions;

import com.google.gson.annotations.Expose;

public class RestException extends RuntimeException {
    private final int status;
    @Expose
    private final String error;
    @Expose
    private final String message;

    public RestException(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
