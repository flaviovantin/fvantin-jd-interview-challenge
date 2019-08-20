package br.com.fvantin.service.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 6641582732401389357L;

    public UnauthorizedException() {
        super("Unauthorized by Star Wars (swapi.co) server.");
    }
}
