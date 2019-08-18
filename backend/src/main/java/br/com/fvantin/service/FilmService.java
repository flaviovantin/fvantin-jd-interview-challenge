package br.com.fvantin.service;

import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.service.dto.FilmDTO;
import br.com.fvantin.service.dto.FilmListDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    final ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
        request.getHeaders().add("user-agent", "spring");
        return execution.execute(request, body);
    };

    public List<FilmDTO> getAllFilms() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        FilmListDTO films = restTemplate.getForObject("https://swapi.co/api/films", FilmListDTO.class);

        // Results from SWAPI.co are paged
        List<FilmDTO> filmDTOs = new ArrayList<>();
        boolean hasMoreResultPage = true;
        do {
            List<FilmDTO> results = films.getResults();
            for (int i = 0; i < results.size(); i++) {
                FilmDTO filmDTO =  results.get(i);
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

    public FilmDTO getFilmById(String filmId) {

        // TODO refactor it!

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors(interceptor).build();
        return restTemplate.getForObject("https://swapi.co/api/films/" + filmId, FilmDTO.class);
    }
}
