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


    public FilmDTO getFilmById(String filmId) {

        return restTemplate.getForObject(SWAPI_API_FILMS + filmId, FilmDTO.class);

    }

//        ResponseEntity<FilmDTO> filmDTOResponseEntity = null;
//        try {
//
//            // TODO https://stackoverflow.com/questions/50239209/spring-resttemplate-how-to-reach-state-to-check-is4xxclienterror-instead-of-r
//
//            filmDTOResponseEntity = restTemplate.exchange(SWAPI_API_FILMS + filmId, HttpMethod.GET, null, FilmDTO.class);
//
////            HttpStatus httpStatus = filmDTOResponseEntity.getStatusCode();
//
//        } catch (RestClientException rce) {
//            System.out.println(">> REST EXC: " + rce);
//
//            System.out.println(">> rce.getMessage(): " + rce.getMessage());
//
//            String status = rce.getgetMessage();
//
//            // NullPointer
////            HttpStatus httpStatus = filmDTOResponseEntity.getStatusCode();
////            System.out.println(">> HttpStatus httpStatus -----------------------------");
////            System.out.println(" >> httpStatus.value(): " + httpStatus.value());
////            System.out.println(" >> httpStatus.name(): " + httpStatus.name());
////            System.out.println(" >> httpStatus.toString(): " + httpStatus.toString());
//
//            switch (status) {
//                case NOT_FOUND:
//                    throw new ResourceNotFoundException(filmId);
//                case INTERNAL_SERVER_ERROR:
//                    throw new InternalServerErrorException();
//                case SERVICE_UNAVAILABLE:
//                    throw new ServiceUnavailableException();
//                case GATEWAY_TIMEOUT:
//                    throw  new GatewayTimeout();
//                case BAD_GATEWAY:
//                    throw new BadGatewayException();
//                default:
//                    throw new RuntimeException();
//            }

//            if (httpStatus.getReasonPhrase().equals(NOT_FOUND.getReasonPhrase())) {
//                throw new ResourceNotFoundException(filmId);
//
//            } else if (httpStatus.getReasonPhrase().equals(INTERNAL_SERVER_ERROR.getReasonPhrase())) {
//                throw new InternalServerErrorException();
//
//            } else if (httpStatus.getReasonPhrase().equals(SERVICE_UNAVAILABLE.getReasonPhrase())) {
//                throw new ServiceUnavailableException();
//
//            } else if (httpStatus.getReasonPhrase().equals(GATEWAY_TIMEOUT.getReasonPhrase())) {
//                throw new ServiceUnavailableException();
//
//            } else {
//                System.out.println(">> Exception: httpStatus: " + httpStatus);
//                throw new RuntimeException();
//            }

//            if (httpStatus.is2xxSuccessful()) {
//                return filmDTOResponseEntity.getBody();
//            } else if (httpStatus.value() == NOT_FOUND.value()) {
//                throw new ResourceNotFoundException(filmId);
//            } else if (httpStatus.value() == INTERNAL_SERVER_ERROR.value()) {
//                throw new InternalServerErrorException();
//            } else if (httpStatus.value() == SERVICE_UNAVAILABLE.value()) {
//                throw new ServiceUnavailableException();
//            } else if (httpStatus.value() == GATEWAY_TIMEOUT.value()) {
//                throw new ServiceUnavailableException();
//            } else {
//                System.out.println(">> Exception: httpStatus: " + httpStatus);
//                throw new RuntimeException();
//            }
//        }

//        return filmDTOResponseEntity.getBody();
//        return restTemplate.getForObject(SWAPI_API_FILMS + filmId, FilmDTO.class);
//    }

    // TODO
//    FORBIDDEN(403, "Forbidden"),
//    NOT_FOUND(404, "Not Found"),
//    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
//    REQUEST_TIMEOUT(408, "Request Timeout"),
//    TOO_MANY_REQUESTS(429, "Too Many Requests"),
//
//    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
//    NOT_IMPLEMENTED(501, "Not Implemented"),
//    BAD_GATEWAY(502, "Bad Gateway"),
//    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
//    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
}
