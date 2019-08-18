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
