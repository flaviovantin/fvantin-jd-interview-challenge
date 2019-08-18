package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

public class StarWarsCharacter extends ResourceSupport {

    private String characterId;
    private String name;

    public StarWarsCharacter() {
        super();
    }

    public StarWarsCharacter(String characterId, String name) {
        this.characterId = characterId;
        this.name = name;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
