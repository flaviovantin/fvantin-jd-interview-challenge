package br.com.fvantin.service;

import br.com.fvantin.service.dto.SpeciesDTO;
import br.com.fvantin.service.dto.SpeciesListDTO;
import br.com.fvantin.service.exception.handler.SWApiResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpeciesService {

    private static final String SWAPI_API_SPECIES = "https://swapi.co/api/species/";
    private RestTemplate restTemplate;

    public SpeciesService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .additionalInterceptors((request, body, execution) -> { // TODO checar se necessario
                    request.getHeaders().add("user-agent", "spring");
                    return execution.execute(request, body);
                })
                .errorHandler(new SWApiResponseErrorHandler())
                .build();
    }

    public List<SpeciesDTO> getAllSpecies() {
        SpeciesListDTO species = restTemplate.getForObject(SWAPI_API_SPECIES, SpeciesListDTO.class);

        // Results from SWAPI.co are paged
        List<SpeciesDTO> speciesDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<SpeciesDTO> results = species.getResults();
            for (SpeciesDTO speciesDTO : results) {
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
        return restTemplate.getForObject(SWAPI_API_SPECIES + speciesId, SpeciesDTO.class);
    }
}
