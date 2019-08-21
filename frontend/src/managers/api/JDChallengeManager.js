import { responseHandle } from '../ResponseHandler'

export const getFilms = () => {
    return fetch('http://localhost:8080/api/jdtest/films')
    .then(responseHandle)
}

export const getCharacters = () => {
    return fetch('http://localhost:8080/api/jdtest/characters', {mode: 'no-cors'})
    .then(responseHandle)
}

export const getSpeciesByFilm = (filmId, characterId) => {
    return fetch('http://localhost:8080/api/jdtest?film_id=' + filmId + '&character_id=' + characterId, {mode: 'no-cors'})
    .then(responseHandle)
}