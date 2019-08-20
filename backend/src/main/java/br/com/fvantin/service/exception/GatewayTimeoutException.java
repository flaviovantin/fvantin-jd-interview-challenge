package br.com.fvantin.service.exception;

public class GatewayTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -1846288774750008841L;

    public GatewayTimeoutException() {
        super("Gateway Timeout on Star Wars (swapi.co) server.");
    }
}
