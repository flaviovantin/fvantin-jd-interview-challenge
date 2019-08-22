import { 
  GET_FILMS, GET_CHARACTERS, GET_SPECIES_BY_FILM, CLEAR_LIST_RESULT, LOADING, CLOSE_ERROR_MODAL 
} from '../actions/JDChallengeActions'
  
  const initialState = {
    films: [],
    characters: [],
    result: {
      characters: [],
      filmName: undefined,
      speciesName: undefined
    },
    errorMessages: undefined,
    loading: false    
  }
  
  /**
   * JDChallenge Reducer
   */
  export default (state = initialState, { type, payload, error }) => {
  
    switch (type) {
      
      case GET_FILMS:
        return {
          ...state,
          films: payload._embedded.starWarsFilmList.sort((a, b) => a.episodeNumber.localeCompare(b.episodeNumber))
        }
  
      case GET_CHARACTERS:
        return {
          ...state,
          characters: payload._embedded.starWarsCharacterList.sort((a, b) => a.name.localeCompare(b.name))
        }

      case GET_SPECIES_BY_FILM:
        return {
            ...state,
            result: payload
        }

      case CLEAR_LIST_RESULT:
        return {
          ...state,
          result: {
            characters: [],
            filmName: undefined,
            speciesName: undefined
          }
        }
    
      case CLOSE_ERROR_MODAL:
        return {
          ...state,
          errorMessages: []
        }
  
      case LOADING:
        return {
          ...state,
          loading: payload
        }
  
      default:
        return state
    }
  }