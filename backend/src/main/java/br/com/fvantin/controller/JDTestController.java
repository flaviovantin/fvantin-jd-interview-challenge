package br.com.fvantin.controller;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.FacadeService;
import br.com.fvantin.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/jdtest")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class JDTestController {

    // TODO tentar usar Autowired depois
//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    private FacadeService facadeService;

    // TODO >>>> Estudar como fazer os redirecionamentos:
    //      /                                       --> Página inicial Welcome
    //      /api                                    --> Página inicial Welcome
    //      /api/jdtest                             --> Página inicial frontend (com arrays preenchidos de film/character)
    //      /api/jdtest?film_id=1&character_id=8    --> Request para "calculo"
//    @GetMapping("/")
//    public HttpEntity<Greetings> slash() {
//        Greetings greetings = new Greetings();
//        ResponseEntity<Greetings> greetingsResponseEntity = new ResponseEntity<>(greetings, HttpStatus.OK);
//        return greetingsResponseEntity;
//    }

    @GetMapping(produces = { "application/hal+json" })
    public Resource<StarWarsSpeciesByFilm> jdtest(@RequestParam(value="film_id") String filmId, @RequestParam(value="character_id") String characterId) {
        // TODO IMPORTANT! Implementing authenticantion via API. Spring XYZ Security ??
        //                 curl -u user:password localhost:8080/api/jdtest
//        Resource<StarWarsSpeciesByFilm> starWarsSpeciesByFilmResource = new Resource<>();
        StarWarsSpeciesByFilm starWarsSpeciesByFilm = facadeService.getSimilarSpeciesByFilm(filmId, characterId);
        if (StringUtils.isEmpty(starWarsSpeciesByFilm.getWarningMessage())) {
//            Resources
        }
        return null;
    }

    // TODO seguir o esquema https://www.baeldung.com/spring-response-status-exception

    @GetMapping(value = "/films", produces = { "application/hal+json" })
    public ResponseEntity<Resources<StarWarsFilm>> getAllFilms() {
        return facadeService.getAllFilms()
                .map(films -> {
                    for (StarWarsFilm starWarsFilm : films) {
                        Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
                        starWarsFilm.add(selfLink);
                    }
                    Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withSelfRel();
                    return ResponseEntity.ok(new Resources<>(films, collectionLink));
                })
                .orElseThrow(BadGatewayException::new);
    }

//    @GetMapping(value = "/films/{filmId}", produces = { "application/hal+json" })
//    public ResponseEntity<Resource<StarWarsFilm>> getFilmById(@PathVariable final String filmId) {
//       return facadeService.getFilmById(filmId)
//                .map(starWarsFilm -> {
//                    Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
//                    starWarsFilm.add(selfLink);
//                    Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withRel("all");
//                    starWarsFilm.add(collectionLink);
//
//                    return ResponseEntity.ok(new Resource<>(starWarsFilm));
//                })
//                .orElseThrow(() -> new FilmNotFoundException(filmId));
//    }

    @GetMapping(value = "/films/{filmId}", produces = { "application/hal+json" })
    public Resource<StarWarsFilm> getFilmById(@PathVariable final String filmId) {
        StarWarsFilm starWarsFilm;
        try {
            starWarsFilm = facadeService.getFilmById(filmId);
            Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
            starWarsFilm.add(selfLink);
            Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withRel("all");
            starWarsFilm.add(collectionLink);
        } catch (ResourceNotFoundException rnfe) {
            String msg = "Star Wars film not found (id: " + filmId + "). Episodes range from 1 to 7.";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        } catch (InternalServerErrorException isee) {
            String msg = "Error occurred on Star Wars (swapi.co) server :(";
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        } catch (ServiceUnavailableException sue) {
            String msg = "Star Wars (swapi.co) server is unavailable. Try it later...";
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, msg);
        } catch (RuntimeException rte) {
            String msg = "Stranger Things occurred :o";
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        }
        return new Resource<>(starWarsFilm);
    }

    // TODO Working below..
//    @GetMapping(value = "/films/{filmId}", produces = { "application/hal+json" })
//    public Resource<StarWarsFilm> getFilmById(@PathVariable final String filmId) {
//        StarWarsFilm starWarsFilm = facadeService.getFilmById(filmId);
//        Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
//        starWarsFilm.add(selfLink);
//        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withRel("all");
//        starWarsFilm.add(collectionLink);
//        return new Resource<>(starWarsFilm);
//    }

    @GetMapping(value = "/characters", produces = { "application/hal+json" })
    public Resources<StarWarsCharacter> getAllCharacters() {
        try {
            final List<StarWarsCharacter> characters = facadeService.getAllCharacters();
            for (StarWarsCharacter starWarsCharacter : characters) {
                Link selfLink = linkTo(methodOn(JDTestController.class).getCharacterById(starWarsCharacter.getCharacterId())).withSelfRel();
                starWarsCharacter.add(selfLink);
            }
            Link collectionLink = linkTo(methodOn(JDTestController.class).getAllCharacters()).withSelfRel();
            return new Resources<>(characters, collectionLink);
        } catch (RestClientException rce) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error requesting SWAPI.co server :(", rce);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops internal server error :(", e);
        }
    }

    @GetMapping(value = "/characters/{characterId}", produces = { "application/hal+json" })
    public Resource<StarWarsCharacter> getCharacterById(@PathVariable final String characterId) {
        StarWarsCharacter starWarsCharacter = facadeService.getCharacterById(characterId);
        Link selfLink = linkTo(methodOn(JDTestController.class).getCharacterById(starWarsCharacter.getCharacterId())).withSelfRel();
        starWarsCharacter.add(selfLink);
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllCharacters()).withRel("all");
        starWarsCharacter.add(collectionLink);
        return new Resource<>(starWarsCharacter);
    }

}
