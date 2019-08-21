import React from 'react';
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import AllReducers from './reducers/AllReducers'
import thunk from 'redux-thunk'
import logger from 'redux-logger'
import './index.css'
import App from './App'
import { BrowserRouter, Switch } from 'react-router-dom'
import * as serviceWorker from './serviceWorker'
import 'normalize.css'

// Store
const store = createStore(
  AllReducers, 
  applyMiddleware(thunk, logger)
  // ,
  // window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__() // TODO: Desabilitar em PROD
)

render(
  <Provider store={store}>
    <BrowserRouter>
      <Switch>
        <App />
      </Switch>
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
)

serviceWorker.unregister();