package br.com.fvantin.service.exception;

public class RequestTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -871620769164057013L;

    public RequestTimeoutException() {
        super("Request Timeout on Star Wars (swapi.co) server.");
    }
}
