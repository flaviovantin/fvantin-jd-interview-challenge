package br.com.fvantin.service;

import br.com.fvantin.service.dto.PeopleDTO;
import br.com.fvantin.service.dto.PeopleListDTO;
import br.com.fvantin.service.exception.handler.SWApiResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    private static final String SWAPI_API_PEOPLE = "https://swapi.co/api/people/";
    private RestTemplate restTemplate;

    @Autowired
    public CharacterService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("user-agent", "spring");
                    return execution.execute(request, body);
                })
                .errorHandler(new SWApiResponseErrorHandler())
                .build();
    }

    public List<PeopleDTO> getAllCharacters() {
        PeopleListDTO people = restTemplate.getForObject(SWAPI_API_PEOPLE, PeopleListDTO.class);

        // Results from SWAPI.co are paged
        List<PeopleDTO> peopleDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<PeopleDTO> results = people.getResults();
            for (PeopleDTO peopleDTO : results) {
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

    // TODO

    public PeopleDTO getCharacterById(String characterId) {
        return restTemplate.getForObject(SWAPI_API_PEOPLE + characterId, PeopleDTO.class);
    }

    public ResponseEntity<PeopleDTO> getCharacterById2(String characterId) {
        return restTemplate.exchange(SWAPI_API_PEOPLE + characterId, HttpMethod.GET, null, PeopleDTO.class);
    }
}
