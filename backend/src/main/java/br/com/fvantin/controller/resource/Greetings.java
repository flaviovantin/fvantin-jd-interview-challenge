package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

public class Greetings extends ResourceSupport {

    private final String welcome;

    public Greetings() {
        this.welcome = "Welcome to JD Backend Integration API - SWAPI.co";
    }

    public String getWelcome() {
        return welcome;
    }
}
