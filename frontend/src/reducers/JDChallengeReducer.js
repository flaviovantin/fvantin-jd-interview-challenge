import { 
  GET_FILMS, GET_CHARACTERS, GET_SPECIES_BY_FILM, LOADING, CLOSE_ERROR_MODAL 
} from '../actions/JDChallengeActions'
  
  const initialState = {
    films: [],  // { filmId: 0, title: 'Loading...' }
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