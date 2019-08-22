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
      result: undefined
    }
  }

  componentWillMount() {
    this.props.screenLoading(false)
  }

  componentDidMount() {
    this.setState({
      showErrorModal: this.props.jdChallengeReducer.errorMessages ? true : false
    })
    this.props.retrieveFilms()
    this.props.retrieveCharacters()
  }

  processSpeciesByFilm = (filmId, characterId) => {
    this.props.screenLoading(true)
    this.props.retrieveSpeciesByFilm(filmId, characterId) // TODO descomentar
  }

  clearFields = () => {
    this.setState({
      filmId: '',         // 'Select' ??
      characterId: '',    // 'Select' ??
      result: ''
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
            <Button bsStyle="primary" onClick={() => this.processSpeciesByFilm(this.state.filmId ,this.state.characterId)}>Search</Button>
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

        
        <Row>&nbsp;</Row>
        <Row>
          <Col xs={12} mdOffset={2} md={7}>
            <Panel>
              <Panel.Heading>Results</Panel.Heading>
              <ListGroup>
                <ListGroupItem>Item 1</ListGroupItem>
                <ListGroupItem>Item 2</ListGroupItem>
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
