import React, { Component } from 'react'
import { Grid, Row } from  'react-bootstrap'

class Home extends Component {

  render() {
    return (
      <div>
        <Grid>
          <h4>Hi, I am Flávio Vantin!</h4>
          <h5>Please, read the following considerations.</h5>


          <h5>As requested by the test guide, the following requirements have been met:</h5>
          <ul>
            <li>You must use Java 8 or superior</li>
            <li>Your application must use Maven or Gradle</li>
            <li>The API your application exposes must use REST principles</li>
            <li>Your API must have the following endpoint: /api/jdtest</li>
            <li>Your API must receive parameters as query parameters. Ex: ?film_id=ID&character_id=ID</li>
            <li>Do not use any already available library to connect to swapi.co, you are required to implement the API calls yourself.</li>
          </ul>

          <h5>The following <i>nice to have but not mandatory</i> items have been met:</h5>
          <ul>
            <li>Use a web framework like Spring or similar</li>
            <li>Your application must use Maven or Gradle</li>
            <li>Handle concurrent API calls</li>
            <li>Use a version control system (GIT)</li>
            <li>Implement an UI</li>
          </ul>

          <h5>About the architecture :</h5>
          <ul>
            <li>API (backend):
              <ul>
                <li>Spring 5 with Spring Boot 2</li>
                <li>Applyed HATEOAS on REST reponses</li>
                <li>Some responses from swapi.co are paged. This way, some requests take longer to complete, e.g., to fill the Characters combobox</li>
              </ul>
            </li>
            <li>Frontend:
              <ul>
                <li>ReactJS with Redux</li>
                <li>React-bootstrap for UI</li>
              </ul>
            </li>
          </ul>
        </Grid>
      </div>
    )
  }
}

export default Home