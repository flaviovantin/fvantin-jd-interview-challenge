package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.dto.FilmDTO;
import br.com.fvantin.service.dto.PeopleDTO;
import br.com.fvantin.service.dto.SpeciesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacadeService {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private SpeciesService speciesService;

    public StarWarsSpeciesByFilm getSimilarSpeciesByFilm(String filmId, String characterId) {

        StarWarsSpeciesByFilm starWarsSpeciesByFilm = new StarWarsSpeciesByFilm();

        // TODO:
        //      1. get JSON of filmId = n
        //          --> a list of characters will be returned
        //      2. get JSON of specific peopleId (characterId)
        //          --> return JSON with details about the people + species type (speciesId = x)
        //          --> check if this people is present in the list of characters in the JSON film
        //          --> if characterId/peopleId don´t exist in that film, retorn error
        //      3. get the species with speciesId = x
        //          --> return JSON with info about the species (name="Droid")
        //          --> only to hold the name
        //      4. get the list of all PeopleDTO and filter it by speciesId = x (Droid)
        //          --> Filter by URL like /spe
        //          --> a list containing all people with id = x
        //      5. go to the list in step 1 and join it with list of step 4 filtering all characters of the movie which peopleId = x
        //          --> a list with all people of species = Droid of the filmId = n
        //      6. return httpResponse to the frontend with the list of step 5

        // Steps 1 and 2
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        PeopleDTO peopleDTO = characterService.getCharacterById(characterId);

        List<String> characters = filmDTO.getCharacters().stream()
                .filter(c -> c.equalsIgnoreCase(peopleDTO.getUrl()))
                .collect(Collectors.toList());
        if (characters.size() == 0) {
            starWarsSpeciesByFilm.setWarningMessage("The character " + peopleDTO.getName() + " is not present in the film " + filmDTO.getTitle() + "! :(");

        } else {

            // Step 3
            String speciesURL = peopleDTO.getSpecies().get(0); // Always only one is returned
            String speciesId = speciesURL.split("/")[5];
            SpeciesDTO speciesDTO = speciesService.getSpeciesById(speciesId);
            String speciesName = speciesDTO.getName();

            // Step 4
            List<PeopleDTO> peopleDTOs = characterService.getAllCharacters();
            List<PeopleDTO> filteredPeopleDTOs = peopleDTOs.stream()
                    .filter(p -> p.getSpecies().stream().findFirst().orElse("").equalsIgnoreCase(speciesURL))
                    .collect(Collectors.toList());

            // Step 5
            List<PeopleDTO> vips = new ArrayList<>();
            filteredPeopleDTOs.forEach(p -> p.getFilms().forEach(f -> {
                if (f.equalsIgnoreCase(filmDTO.getUrl())) vips.add(p);
            }));

            List<String> species = new ArrayList<>();
            vips.forEach(vip -> species.add(vip.getName()));

            starWarsSpeciesByFilm.setFilmName(filmDTO.getTitle());
            starWarsSpeciesByFilm.setSpecieName(speciesName);
            starWarsSpeciesByFilm.setSpecies(species);
            starWarsSpeciesByFilm.setWarningMessage(null);
        }

        // Step 6
        return starWarsSpeciesByFilm;
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
        PeopleDTO peopleDTO = characterService.getCharacterById(characterId);
        String id = peopleDTO.getUrl().split("/")[5];
        return new StarWarsCharacter(id, peopleDTO.getName());
    }
}
