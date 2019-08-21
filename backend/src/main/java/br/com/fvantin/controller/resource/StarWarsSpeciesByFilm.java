package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class StarWarsSpeciesByFilm extends ResourceSupport {

    private String filmName;
    private String speciesName;
    private List<String> characters;

    // TODO --> 3 characters of the species 'Droid' appear in the film 'A New Hope': C-3PO, R2-D2, R5-D3
    // TODO --> No 'Droid' characters appear in the movie 'A New Hope' :(

    public StarWarsSpeciesByFilm() {
        super();
    }

    public StarWarsSpeciesByFilm(String filmName, String speciesName, List<String> characters) {
        this.filmName = filmName;
        this.speciesName = speciesName;
        this.characters = characters;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }
}
