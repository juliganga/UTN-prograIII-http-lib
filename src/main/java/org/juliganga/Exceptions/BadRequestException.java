package org.juliganga.Exceptions;

import java.net.http.HttpResponse;

public class BadRequestException extends Exception {
    private int httpcode;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, int httpcode)
    {
        super(message);
        this.httpcode = httpcode;
    }

    public int getHttpCode() {
        return httpcode;
    }
}
