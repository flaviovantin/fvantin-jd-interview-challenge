package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class StarWarsSpeciesByFilm extends ResourceSupport {

    private String filmName;
    private String specieName;
    private List<String> species;
    private String warningMessage;

    public StarWarsSpeciesByFilm() {
        super();
    }

    public StarWarsSpeciesByFilm(String filmName, String specieName, List<String> species) {
        this.filmName = filmName;
        this.specieName = specieName;
        this.species = species;
    }

    public StarWarsSpeciesByFilm(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
