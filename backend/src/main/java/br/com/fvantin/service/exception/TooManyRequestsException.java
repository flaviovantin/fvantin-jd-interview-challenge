package br.com.fvantin.service.exception;

public class TooManyRequestsException extends RuntimeException {

    private static final long serialVersionUID = -983515686863083020L;

    public TooManyRequestsException() {
        super("To oMany Requests on Star Wars (swapi.co) server.");
    }
}
