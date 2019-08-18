package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.dto.FilmDTO;
import br.com.fvantin.service.dto.PeopleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacadeService {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CharacterService characterService;


    public StarWarsSpeciesByFilm getSimilarSpeciesByFilm(String filmId, String characterId) {
        return null;
    }


    public List<StarWarsFilm> getAllFilms() {
        List<FilmDTO> filmDTOs = filmService.getAllFilms();
        List<StarWarsFilm> starWarsFilms = new ArrayList<>();
        for (FilmDTO filmDTO : filmDTOs) {
            String id = filmDTO.getUrl().split("/")[5];
            starWarsFilms.add(new StarWarsFilm(id, filmDTO.getTitle(), filmDTO.getEpisodeId()));
        }
        return starWarsFilms;
    }

    public StarWarsFilm getFilmById(String filmId) {
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        String id = filmDTO.getUrl().split("/")[5];
        return new StarWarsFilm(id, filmDTO.getTitle(), filmDTO.getEpisodeId());
    }


    public List<StarWarsCharacter> getAllCharacters() {
        List<PeopleDTO> peopleDTOs = characterService.getAllCharacters();
        List<StarWarsCharacter> starWarsCharacters = new ArrayList<>();
        for (PeopleDTO peopleDTO : peopleDTOs) {
            String id = peopleDTO.getUrl().split("/")[5];
            starWarsCharacters.add(new StarWarsCharacter(id, peopleDTO.getName()));
        }
        return starWarsCharacters;
    }

    public StarWarsCharacter getCharacterById(String characterId) {
        return null;
    }
}
