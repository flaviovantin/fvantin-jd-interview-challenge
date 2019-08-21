package br.com.fvantin.service;

import br.com.fvantin.service.dto.FilmDTO;
import br.com.fvantin.service.dto.FilmListDTO;
import br.com.fvantin.service.exception.handler.SWApiResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    private static final String SWAPI_API_FILMS = "https://swapi.co/api/films/";
    private RestTemplate restTemplate;

    @Autowired
    public FilmService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("user-agent", "spring");
                    return execution.execute(request, body);
                })
                .errorHandler(new SWApiResponseErrorHandler())
                .build();
    }

    public List<FilmDTO> getAllFilms() {
        FilmListDTO films = restTemplate.getForObject(SWAPI_API_FILMS, FilmListDTO.class);
        // Results from SWAPI.co are paged
        List<FilmDTO> filmDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<FilmDTO> results = films.getResults();
            for (FilmDTO filmDTO : results) {
                filmDTOs.add(filmDTO);
            }
            if (films.getNext() == null) {
                hasMoreResultPage = false;
            } else {
                films = restTemplate.getForObject(films.getNext(), FilmListDTO.class);
            }
        } while (hasMoreResultPage);
        return filmDTOs;
    }

    public FilmDTO getFilmById(Integer filmId) {
        return restTemplate.getForObject(SWAPI_API_FILMS + filmId, FilmDTO.class);
    }
}
