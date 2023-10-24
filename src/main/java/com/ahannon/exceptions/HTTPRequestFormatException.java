package com.ahannon.exceptions;

public class HTTPRequestFormatException extends Exception {
    public HTTPRequestFormatException () {

    }

    public HTTPRequestFormatException (String message) {
        super (message);
    }

    public HTTPRequestFormatException (Throwable cause) {
        super (cause);
    }

    public HTTPRequestFormatException (String message, Throwable cause) {
        super (message, cause);
    }
}
