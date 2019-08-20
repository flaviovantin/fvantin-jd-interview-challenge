package br.com.fvantin.controller;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.FacadeService;
import br.com.fvantin.service.exception.*;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/jdtest")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class JDTestController {

    public static final String APPLICATION_HAL_JSON = "application/hal+json";

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

    @GetMapping(value = "/error", produces = { APPLICATION_HAL_JSON })
    public HttpResponse error() {
        return null;
    }

    @GetMapping(produces = { APPLICATION_HAL_JSON })
    public Resource<StarWarsSpeciesByFilm> jdtest(@Valid @RequestParam(value="film_id") Integer filmId, @Valid @RequestParam(value="character_id") String characterId) {
        // TODO IMPORTANT! Implementing authenticantion via API. Spring XYZ Security ??
        //                 curl -u user:password localhost:8080/api/jdtest
        // Resource<StarWarsSpeciesByFilm> starWarsSpeciesByFilmResource = new Resource<>();
        StarWarsSpeciesByFilm starWarsSpeciesByFilm = facadeService.getSimilarSpeciesByFilm(filmId, characterId);
        if (StringUtils.isEmpty(starWarsSpeciesByFilm.getWarningMessage())) {
            // Resources
        }
        return null;
    }

    @GetMapping(value = "/films", produces = { APPLICATION_HAL_JSON })
    public Resources<StarWarsFilm> getAllFilms() {
        List<StarWarsFilm> films = null;
        try {
            films = facadeService.getAllFilms();
            for (StarWarsFilm starWarsFilm : films) {
                Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
                starWarsFilm.add(selfLink);
            }
        } catch (RuntimeException rte) {
            if (rte instanceof ResourceNotFoundException) {
                String msg = "No movies found on swapi.co server. This is very weird...";
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, rte);
            } else {
                handleException(rte);
            }
        }
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withSelfRel();
        return new Resources<>(films, collectionLink);
    }

    @GetMapping(value = "/films/{filmId}", produces = { APPLICATION_HAL_JSON })
    public Resource<StarWarsFilm> getFilmById(@Valid @PathVariable final Integer filmId) {
        StarWarsFilm starWarsFilm = null;
        try {
            starWarsFilm = facadeService.getFilmById(filmId);
            Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
            starWarsFilm.add(selfLink);
            Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withRel("all");
            starWarsFilm.add(collectionLink);
        } catch (RuntimeException rte) {
            if (rte instanceof ResourceNotFoundException) {
                StringBuilder msg = new StringBuilder( "Star Wars film not found (id: ").append(filmId).append("). Episodes range from 1 to 7.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg.toString(), rte);
            } else {
                handleException(rte);
            }
        }
        return new Resource<>(starWarsFilm);
    }

    @GetMapping(value = "/characters", produces = { APPLICATION_HAL_JSON })
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

    @GetMapping(value = "/characters/{characterId}", produces = { APPLICATION_HAL_JSON })
    public Resource<StarWarsCharacter> getCharacterById(@PathVariable final String characterId) {
        StarWarsCharacter starWarsCharacter = facadeService.getCharacterById(characterId);
        Link selfLink = linkTo(methodOn(JDTestController.class).getCharacterById(starWarsCharacter.getCharacterId())).withSelfRel();
        starWarsCharacter.add(selfLink);
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllCharacters()).withRel("all");
        starWarsCharacter.add(collectionLink);
        return new Resource<>(starWarsCharacter);
    }

    // Using Spring 5 ResponseStatusException schema
    private void handleException(RuntimeException rte) {
        if (rte instanceof BadRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, rte.getMessage(), rte);
        } else if (rte instanceof UnauthorizedException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, rte.getMessage(), rte);
        } else if (rte instanceof ForbiddenException) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, rte.getMessage(), rte);
        } else if (rte instanceof ResourceNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, rte.getMessage(), rte);
        } else if (rte instanceof MethodNotAllowedException) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, rte.getMessage(), rte);
        } else if (rte instanceof RequestTimeoutException) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, rte.getMessage(), rte);
        } else if (rte instanceof TooManyRequestsException) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, rte.getMessage(), rte);
        } else if (rte instanceof InternalServerErrorException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, rte.getMessage(), rte);
        } else if (rte instanceof BadGatewayException) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, rte.getMessage(), rte);
        } else if (rte instanceof ServiceUnavailableException) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, rte.getMessage(), rte);
        } else if (rte instanceof GatewayTimeoutException) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, rte.getMessage(), rte);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR); //, rte.getMessage());
        }
    }
}
