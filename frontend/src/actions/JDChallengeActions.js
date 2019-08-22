import { getFilms, getCharacters, getSpeciesByFilm } from '../managers/api/JDChallengeManager'

// Actions

export const GET_FILMS = 'GET_FILMS'
export const GET_CHARACTERS = 'GET_CHARACTERS'
export const GET_SPECIES_BY_FILM = 'GET_SPECIES_BY_FILM'
export const CLEAR_LIST_RESULT = 'CLEAR_LIST_RESULT'
export const CLOSE_ERROR_MODAL = 'CLOSE_ERROR_MODAL'
export const LOADING = 'LOADING'

// Action methods

export const retrieveFilms = () => {
  return dispatch => {
    getFilms()
      .then(response => {
        dispatch({
          type: GET_FILMS,
          payload: response
        })
        dispatch({
          type: LOADING,
          payload: false
        })
      })
      .catch(error => {
        console.log(error)
      })
  }
}

export const retrieveCharacters = () => {
  return dispatch => {
    getCharacters()
      .then(response => {
        dispatch({
          type: GET_CHARACTERS,
          payload: response
        })
        dispatch({
          type: LOADING,
          payload: false
        })
      })
      .catch(error => {
        console.log(error)
      })
  }
}

export const retrieveSpeciesByFilm = (filmId, characterId) => {
  return dispatch => {
    getSpeciesByFilm(filmId, characterId)
      .then(response => {
        dispatch({
          type: GET_SPECIES_BY_FILM,
          payload: response
        })
        dispatch({
          type: LOADING,
          payload: false
        })
      })
      .catch(error => {
        console.log(error)
        dispatch({
          type: LOADING,
          payload: false
        })
      })
  }
}

export const clearListResult = () => ({
  type: CLEAR_LIST_RESULT
})

export const closeErrorModal = () => ({
  type: CLOSE_ERROR_MODAL
})

export const screenLoading = (isLoading) => ({
  type: LOADING,
  payload: isLoading
})
