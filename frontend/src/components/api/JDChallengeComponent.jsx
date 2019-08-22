import React, { Component } from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import * as actions from '../../actions/JDChallengeActions'
import { Grid, ControlLabel, Button, Row, FormGroup, Col, FormControl, Modal, ListGroup, ListGroupItem, Panel } from 'react-bootstrap'
import { css } from 'react-emotion'
import { BarLoader } from 'react-spinners'

const override = css `display: block; margin: 0 auto; border-color: red;`

class JDChallengeComponent extends Component {

  constructor (props) {
    super(props)

    this.state = {
      filmId: undefined,
      characterId: undefined,
      //films: undefined,
      //characters: undefined,
      //result: undefined
    }
  }

  componentWillMount() {
    this.props.screenLoading(false)
  }

  componentDidMount() {
    this.setState({
      //result: this.props.jdChallengeReducer.result,
      showErrorModal: this.props.jdChallengeReducer.errorMessages ? true : false
    })
    this.props.retrieveFilms()
    this.props.retrieveCharacters()
  }

  processSpeciesByFilm = (filmId, characterId) => {
    this.props.screenLoading(true)
    this.props.retrieveSpeciesByFilm(filmId, characterId) // TODO descomentar
    //this.setState({
    //  result: undefined
    //})
  }

  // handleListHeader = (result) => {
  //   alert('dsfsdfsd result:' + result)
  //   if (!result) {
  //     return 'No results yet... :|'
  //   }

  //   if (result && result.characters) {

  //     if (result.characters.lenght > 0) {
  //       return 'The following character(s) of the species \'' + result.speciesName + '\' appear(s) in the film \'' + result.filmName + '\':'
  //     } else {
  //       return 'Sorry, no \'' + result.speciesName + '\' characters appear in the movie \'' + result.filmName + '\'. Try again!'
  //     }

  //   } else {
  //     return 'aaaa'
  //   }
  // }

  // handleListItems = (result) => {
  //   alert('444444444 result:' + result)
  //   if (!result) {
  //     return <ListGroupItem>222</ListGroupItem>
  //   }

  //   if (result && result.characters) {

  //     if (result.characters.lenght > 0) {
  //       return result.characters.sort().map((name, i) => (
  //         <ListGroupItem>{name}</ListGroupItem>
  //       ))
  //     } else {
  //       return <ListGroupItem>111</ListGroupItem>
  //     }

  //   } else {
  //     return 'deu ruim'
  //   }
  // }

  clearFields = () => {
    this.setState({
      filmId: undefined,         // 'Select' ??
      characterId: undefined //,    // 'Select' ??
      //result: undefined
    })
  }

  handleHide = () => {
    this.props.closeErrorModal();
  }

  handleFilmChange = (e) => {
    console.log('>>> e.target:', e.target)
    console.log('>>> e.target.value:', e.target.value)
    console.log('>>> e.target.title:', e.target.title)
    this.setState({ filmId: e.target.value })
  }

  handleCharacterChange = (e) => {
    this.setState({ characterId: e.target.value })
  }

  render() {

    // let personas = !this.props.jdChallengeReducer.result ? undefined : this.props.jdChallengeReducer.result.characters
    // console.log('>> this.props.jdChallengeReducer.result: ', this.props.jdChallengeReducer.result)
    // console.log('>> this.props.jdChallengeReducer.result.characters: ', this.props.jdChallengeReducer.result ? this.props.jdChallengeReducer.result.characters : 'undefinidos')
    // console.log('>> personas: ', personas)

    // AQUI TA A TRETA 1

    console.log('>> this.props.jdChallengeReducer.result.characters: ', this.props.jdChallengeReducer.result.characters)
    console.log('>> this.props.jdChallengeReducer.result.characters.lenght > 0: ', this.props.jdChallengeReducer.result.characters.lenght > 0)

    return (
      <div>
      <Grid>
        <Row>&nbsp;</Row>
        <Row >
          <Col xs={12} mdOffset={2} md={10}>
            <h4>Species on Star Wars films</h4>
          </Col>
        </Row>
        
        <Row>&nbsp;</Row>
        <Row>
          <Col xs={12} mdOffset={2} md={3}>
            {/* <ControlLabel>Select a film</ControlLabel> */}
            <FormControl 
              componentClass="select"
              value={this.props.jdChallengeReducer.films.title}
              onChange={this.handleFilmChange}>
              <option value="">Select a film...</option>
              {
                this.props.jdChallengeReducer.films.map((film, i) => (
                  <option value={film.filmId}>{film.title}</option>
                ))
              }
            </FormControl>
          </Col>
          <Col xs={12} mdOffset={0} md={3}>
            {/* <ControlLabel>Select a character</ControlLabel> */}
            <FormControl 
              componentClass="select"
              value={this.props.jdChallengeReducer.characters.name}
              onChange={this.handleCharacterChange}>
              <option value="">Select a character...</option>
              {
                this.props.jdChallengeReducer.characters.map((character, i) => (
                  <option value={character.characterId}>{character.name}</option>
                ))
              }
            </FormControl>
          </Col>
          <Col xs={12} md={2}>
            <ControlLabel>&nbsp;</ControlLabel>
            <Button bsStyle="primary" onClick={() => this.processSpeciesByFilm(this.state.filmId, this.state.characterId)}>Search</Button>
          </Col>
        </Row>

        <Row>&nbsp;</Row>
        <Row>&nbsp;</Row>
        <Col xs={12} mdOffset={2} md={7}>
          {
            <BarLoader
              className={override}
              sizeUnit={"px"}
              height={1}
              heightUnit={'px'}
              widthUnit={'%'}
              width={100}
              color={'#337ab7'}
              loading={this.props.jdChallengeReducer.loading}/>
          }
        </Col>

{
 /*  "filmName": "A New Hope",
  "speciesName": "Droid",
  "characters": [
    "C-3PO",
    "R2-D2",
    "R5-D4"
  ],
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/jdtest?film_id=1&character_id=2"
    },
    "films": {
      "href": "http://localhost:8080/api/jdtest/films"
    },
    "characters": {
      "href": "http://localhost:8080/api/jdtest/characters"
    }
  } 
  
// 3 characters of the species 'Droid' appear in the film 'A New Hope': C-3PO, R2-D2, R5-D3
// No 'Droid' characters appear in the movie 'A New Hope' :(

  */
}
        
        <Row>&nbsp;</Row>
        <Row>
          <Col xs={12} mdOffset={2} md={7}>
            <Panel>
              <Panel.Heading>
              {

                // AQUI TA A TRETA 2

                this.props.jdChallengeReducer.result.characters.lenght > 0
                  ? 'The following character(s) of the species \'' + this.props.jdChallengeReducer.result.speciesName + '\' appear(s) in the film \'' + this.props.jdChallengeReducer.result.filmName + '\':'
                  : this.props.jdChallengeReducer.result.speciesName 
                    ? 'Sorry, no \'' + this.props.jdChallengeReducer.result.speciesName + '\' characters appear in the movie \'' + this.props.jdChallengeReducer.result.filmName + '\'. Try again!'
                    : 'No results yet... :|'

                // this.props.jdChallengeReducer.result
                //   ? this.props.jdChallengeReducer.result.characters | this.props.jdChallengeReducer.result.characters.lenght > 0 
                //     ? 'The following character(s) of the species \'' + this.props.jdChallengeReducer.result.speciesName + '\' appear(s) in the film \'' + this.props.jdChallengeReducer.result.filmName + '\':'
                //     : 'Sorry, no \'' + this.props.jdChallengeReducer.result.speciesName + '\' characters appear in the movie \'' + this.props.jdChallengeReducer.result.filmName + '\'. Try again!'
                //   : 'No results yet... :|'
                
              }
              </Panel.Heading>
              <ListGroup>
              {
                this.props.jdChallengeReducer.result.characters.lenght > 0
                  ? this.props.jdChallengeReducer.result.characters.sort().map((name, i) => (
                      <ListGroupItem>{name}</ListGroupItem>
                    ))
                  : this.props.jdChallengeReducer.result.speciesName 
                    ? <ListGroupItem>111</ListGroupItem>
                    : <ListGroupItem>222</ListGroupItem>

                  // this.props.jdChallengeReducer.result.characters
                  //   ? this.props.jdChallengeReducer.result.characters.lenght > 0 
                  //     ? 'mostra algo em baixo' 
                  //     : 'mostrar vazio'
                  //   : <ListGroupItem></ListGroupItem>

                //JSON.stringify(this.props.jdChallengeReducer.result)
              }
              </ListGroup>
            </Panel>
          </Col>
        </Row>

        

      </Grid>

      {/* <Modal show={this.props.jdChallengeReducer.errorMessages.length > 0}>
        <Modal.Header><Modal.Title>Oops! Error occurred :(</Modal.Title></Modal.Header>
        <Modal.Body>
          <ul>{this.props.jdChallengeReducer.errorMessages.map(err => { return <li>{err}</li> })}</ul>
        </Modal.Body>
        <Modal.Footer><Button onClick={() => this.handleHide()}>Close</Button></Modal.Footer>
      </Modal> */}
      </div>
    )
  }
}

// function formataValor(valor) {
//     return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(valor); // '$100.00'
// }

function mapStateToProps(state) {
  return { 
    jdChallengeReducer: state.JDChallengeReducer
  }
}

const mapDispacthToProps = dispacth => bindActionCreators(actions, dispacth);

export default connect(mapStateToProps, mapDispacthToProps)(JDChallengeComponent)
