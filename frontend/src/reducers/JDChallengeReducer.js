import { 
    GET_FILMS, GET_CHARACTERS, GET_SPECIES_BY_FILM, LOADING, CLOSE_ERROR_MODAL 
  } from '../actions/JDChallengeActions'
  
  const initialState = {
    films: [],
    characters: [],
    errorMessages: [],
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
          films: payload,
        }
  
      case GET_CHARACTERS:
      return {
        ...state,
        characters: payload,
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