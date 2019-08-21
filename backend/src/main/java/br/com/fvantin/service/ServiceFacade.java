package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.dto.FilmDTO;
import br.com.fvantin.service.dto.PeopleDTO;
import br.com.fvantin.service.dto.SpeciesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceFacade {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private SpeciesService speciesService;

    public StarWarsSpeciesByFilm getSimilarSpeciesByFilm(@Valid Integer filmId, @Valid Integer characterId) {

        // Gets data from filmId passed as parameter, and its list of characters
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        List<String> charactersURLsFromFilm = filmDTO.getCharacters();

        // Gets data from the characterId passed as parameter, and its species
        PeopleDTO peopleDTO = characterService.getCharacterById(characterId);
        String expectedSpeciesURL = peopleDTO.getSpecies().get(0); // Always only one is returned
        SpeciesDTO speciesDTO = speciesService.getSpeciesById(extractId(expectedSpeciesURL));
        String expectedSpeciesName = speciesDTO.getName();

        // Gets all characters from the expected species
        List<String> charactersURLsFromSpecies = speciesDTO.getPeople();

        // Intersects the lists of characters
        List<String> vips = charactersURLsFromFilm.stream()
                .filter(charactersURLsFromSpecies::contains)
                .collect(Collectors.toList());

        // With a list containing only those characters from the same species, and from the same film, get their names.
        List<String> names = new ArrayList<>();
        vips.stream()
                .map(url -> extractId(url))
                .map(id -> characterService.getCharacterById(id))
                .forEach(pDTO -> names.add(pDTO.getName()));
        return new StarWarsSpeciesByFilm(filmDTO.getTitle(), expectedSpeciesName, names);
    }

    public List<StarWarsFilm> getAllFilms() {
        List<FilmDTO> filmDTOs = filmService.getAllFilms();
        List<StarWarsFilm> starWarsFilms = new ArrayList<>();
        for (FilmDTO filmDTO : filmDTOs) {
            starWarsFilms.add(new StarWarsFilm(extractId(filmDTO.getUrl()), filmDTO.getTitle(), filmDTO.getEpisodeId()));
        }
        return starWarsFilms;
    }

    public StarWarsFilm getFilmById(Integer filmId)  {
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        return new StarWarsFilm(extractId(filmDTO.getUrl()), filmDTO.getTitle(), filmDTO.getEpisodeId());
    }

    public List<StarWarsCharacter> getAllCharacters() {
        List<PeopleDTO> peopleDTOs = characterService.getAllCharacters();
        List<StarWarsCharacter> starWarsCharacters = new ArrayList<>();
        for (PeopleDTO peopleDTO : peopleDTOs) {
            starWarsCharacters.add(new StarWarsCharacter(extractId(peopleDTO.getUrl()), peopleDTO.getName()));
        }
        return starWarsCharacters;
    }

    public StarWarsCharacter getCharacterById(Integer characterId) {
        PeopleDTO peopleDTO = characterService.getCharacterById(characterId);
        return new StarWarsCharacter(extractId(peopleDTO.getUrl()), peopleDTO.getName());
    }

    private int extractId(String url) {
        return Integer.parseInt(url.split("/")[5]);
    }
}
