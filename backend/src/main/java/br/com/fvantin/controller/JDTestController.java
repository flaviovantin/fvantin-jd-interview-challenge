package br.com.fvantin.controller;

import br.com.fvantin.controller.resource.StarWarsCharacter;
import br.com.fvantin.controller.resource.StarWarsFilm;
import br.com.fvantin.controller.resource.StarWarsSpeciesByFilm;
import br.com.fvantin.service.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/jdtest")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class JDTestController {

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

    @GetMapping(value = "/films", produces = { "application/hal+json" })
    public Resources<StarWarsFilm> getAllFilms() {
        final List<StarWarsFilm> films = facadeService.getAllFilms();
        for (StarWarsFilm starWarsFilm : films) {
            Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
            starWarsFilm.add(selfLink);
        }
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withSelfRel();
        return new Resources<>(films, collectionLink);
    }

    @GetMapping(value = "/films/{filmId}", produces = { "application/hal+json" })
    public Resource<StarWarsFilm> getFilmById(@PathVariable final String filmId) {
        StarWarsFilm starWarsFilm = facadeService.getFilmById(filmId);
        Link selfLink = linkTo(methodOn(JDTestController.class).getFilmById(starWarsFilm.getFilmId())).withSelfRel();
        starWarsFilm.add(selfLink);
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllFilms()).withRel("all");
        starWarsFilm.add(collectionLink);
        return new Resource<>(starWarsFilm);
    }

    @GetMapping(value = "/characters", produces = { "application/hal+json" })
    public Resources<StarWarsCharacter> getAllCharacters() {
        final List<StarWarsCharacter> characters = facadeService.getAllCharacters();
        for (StarWarsCharacter starWarsCharacter : characters) {
            Link selfLink = linkTo(methodOn(JDTestController.class).getCharacterById(starWarsCharacter.getCharacterId())).withSelfRel();
            starWarsCharacter.add(selfLink);
        }
        Link collectionLink = linkTo(methodOn(JDTestController.class).getAllCharacters()).withSelfRel();
        return new Resources<>(characters, collectionLink);
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
