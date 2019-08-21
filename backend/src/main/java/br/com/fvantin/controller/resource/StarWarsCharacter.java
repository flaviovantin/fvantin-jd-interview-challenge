package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

public class StarWarsCharacter extends ResourceSupport {

    private Integer characterId;
    private String name;

    public StarWarsCharacter() {
        super();
    }

    public StarWarsCharacter(Integer characterId, String name) {
        this.characterId = characterId;
        this.name = name;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
