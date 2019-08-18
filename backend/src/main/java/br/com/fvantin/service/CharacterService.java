package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.service.dto.PeopleDTO;
import br.com.fvantin.service.dto.PeopleListDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    final ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
        request.getHeaders().add("user-agent", "spring");
        return execution.execute(request, body);
    };

    public List<PeopleDTO> getAllCharacters() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        PeopleListDTO people = restTemplate.getForObject("https://swapi.co/api/people", PeopleListDTO.class);

        // Results from SWAPI.co are paged
        List<PeopleDTO> peopleDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<PeopleDTO> results = people.getResults();
            for (int i = 0; i < results.size(); i++) {
                PeopleDTO peopleDTO =  results.get(i);
                peopleDTOs.add(peopleDTO);
            }
            if (people.getNext() == null) {
                hasMoreResultPage = false;
            } else {
                people = restTemplate.getForObject(people.getNext(), PeopleListDTO.class);
            }
        } while (hasMoreResultPage);
        return peopleDTOs;
    }

    public PeopleDTO getCharacterById(String characterId) {

        // TODO refactor it!

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        return restTemplate.getForObject("https://swapi.co/api/people/" + characterId, PeopleDTO.class);
    }

}
