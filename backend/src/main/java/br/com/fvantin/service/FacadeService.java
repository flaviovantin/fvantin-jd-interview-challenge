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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacadeService {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private SpeciesService speciesService;

    public StarWarsSpeciesByFilm getSimilarSpeciesByFilm(Integer filmId, String characterId) {

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
        //          --> a list containing all people with speciesId = x
        //      5. go to the list in step 1 and join it with list of step 4 filtering all characters of the movie which peopleId = x
        //          --> a list with all people of species = Droid of the filmId = n
        //      6. return httpResponse to the frontend with the list of step 5

        // Steps 1 and 2
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        PeopleDTO peopleDTO = characterService.getCharacterById(characterId);

        // TODO MUDAR ABAIXO:
        //      1. Guardar a lista characters que tem todos os personagens do filmId = 1
        //          List<String> characters = filmDTO.getCharacters();
        //          1.1. Se filmId nao existir, lançar exceção
        //      2. Pegar a especie referente ao characterId = 2
        //          PeopleDTO peopleDTO = characterService.getCharacterById(characterId);
        //          String speciesId = speciesURL.split("/")[5];
        //          SpeciesDTO speciesDTO = speciesService.getSpeciesById(speciesId);
        //          String speciesName = speciesDTO.getName();
        //          2.1. Se peopleId nao existir, lançar exceção
        //      3. Filtrar a lista characters (passo 1) pegando somente os personagens que são da especie speciesId = 'Droid'
        //          3.1. Se não há personagens da speciesId = 'Droid' na lista characters, retornar [] vazio e mensagem
        //      4. Retornar a lista

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

            // Step 4 // TODO esse passo talvez nao seja necessario
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
            starWarsFilms.add(new StarWarsFilm(Integer.parseInt(id), filmDTO.getTitle(), filmDTO.getEpisodeId()));
        }
        return starWarsFilms;
//        try {
//            List<FilmDTO> filmDTOs = filmService.getAllFilms();
//            List<StarWarsFilm> starWarsFilms = new ArrayList<>();
//            for (FilmDTO filmDTO : filmDTOs) {
//                String id = filmDTO.getUrl().split("/")[5];
//                starWarsFilms.add(new StarWarsFilm(id, filmDTO.getTitle(), filmDTO.getEpisodeId()));
//            }
//            return Optional.of(starWarsFilms);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
    }

//    public Optional<StarWarsFilm> getFilmById(String filmId)  {
//        try {
//            FilmDTO filmDTO = filmService.getFilmById(filmId);
//            String id = filmDTO.getUrl().split("/")[5];
//            return Optional.of(new StarWarsFilm(id, filmDTO.getTitle(), filmDTO.getEpisodeId()));
//        } catch (RestClientException re) {
//            return Optional.empty();
//        }
//    }

    public StarWarsFilm getFilmById(Integer filmId)  {
        FilmDTO filmDTO = filmService.getFilmById(filmId);
        String id = filmDTO.getUrl().split("/")[5];
        return new StarWarsFilm(Integer.parseInt(id), filmDTO.getTitle(), filmDTO.getEpisodeId());
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
//        ResponseEntity<PeopleDTO> peopleDTOResponseEntity = characterService.getCharacterById(characterId);
//        peopleDTOResponseEntity.
        String id = peopleDTO.getUrl().split("/")[5];
        return new StarWarsCharacter(id, peopleDTO.getName());
    }
}
