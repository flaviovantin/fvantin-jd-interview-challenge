package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.dto.FilmListDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwapiIntegrationService {

    final ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
        request.getHeaders().add("user-agent", "spring");
        return execution.execute(request, body);
    };

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

    public StarWarsSpeciesByFilm getAllCharacterSpeciesByFilm(String filmId, String characterId) {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        FilmListDTO filmListDTO = restTemplate.getForObject("https://swapi.co/api/films", FilmListDTO.class);


        List<String> characters = new ArrayList<>();
        characters.add("Fulano 1");
        characters.add("Fulana 2");
//        return new Characters(characters);
        return null;
    }


}
