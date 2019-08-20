package br.com.fvantin.controller.resource;

import org.springframework.hateoas.ResourceSupport;

public class StarWarsFilm extends ResourceSupport {

    private Integer filmId;
    private String title;
    private String episodeNumber;

    public StarWarsFilm() {
        super();
    }

    public StarWarsFilm(Integer filmId, String title, String episodeNumber) {
        this.filmId = filmId;
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
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
