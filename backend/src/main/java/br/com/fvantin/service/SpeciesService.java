package br.com.fvantin.service;

import br.com.fvantin.service.dto.SpeciesDTO;
import br.com.fvantin.service.dto.SpeciesListDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpeciesService {

    final ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
        request.getHeaders().add("user-agent", "spring");
        return execution.execute(request, body);
    };

    public List<SpeciesDTO> getAllSpecies() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        SpeciesListDTO species = restTemplate.getForObject("https://swapi.co/api/species", SpeciesListDTO.class);

        // Results from SWAPI.co are paged
        List<SpeciesDTO> speciesDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<SpeciesDTO> results = species.getResults();
            for (int i = 0; i < results.size(); i++) {
                SpeciesDTO speciesDTO =  results.get(i);
                speciesDTOs.add(speciesDTO);
            }
            if (species.getNext() == null) {
                hasMoreResultPage = false;
            } else {
                species = restTemplate.getForObject(species.getNext(), SpeciesListDTO.class);
            }
        } while (hasMoreResultPage);
        return speciesDTOs;
    }

    public SpeciesDTO getSpeciesById(String speciesId) {

        // TODO refactor it!

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        return restTemplate.getForObject("https://swapi.co/api/species/" + speciesId, SpeciesDTO.class);
    }
}
