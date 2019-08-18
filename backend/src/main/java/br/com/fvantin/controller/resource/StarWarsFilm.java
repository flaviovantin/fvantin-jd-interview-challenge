package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

public class StarWarsFilm extends ResourceSupport {

    private String filmId;
    private String title;
    private String episodeNumber;

    public StarWarsFilm() {
        super();
    }

    public StarWarsFilm(String filmId, String title, String episodeNumber) {
        this.filmId = filmId;
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}
