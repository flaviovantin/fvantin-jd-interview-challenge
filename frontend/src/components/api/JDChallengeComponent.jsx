import React, { Component } from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import * as actions from '../../actions/JDChallengeActions'
import { Grid, ControlLabel, Button, Row, FormGroup, Col, FormControl, Modal } from 'react-bootstrap'
import { css } from 'react-emotion'
import { BarLoader } from 'react-spinners'

const override = css `display: block; margin: 0 auto; border-color: red;`

class JDChallengeComponent extends Component {

  constructor (props) {
    super(props)

    this.state = {
        filmId: undefined,
        characterId: undefined,
        films: [],
        characters: [],
        result: undefined
   //   tipoCarga: undefined,
   //   distancia: undefined,
   //   numeroEixos: undefined,
   //   margemLucro: undefined,
   //   totalPedagios: undefined,
   //   totalFrete: undefined
    }
  }

  componentWillMount() {
    this.props.screenLoading(false)
    this.props.retrieveFilms()
  }

  componentDidMount() {
    this.setState({
        showErrorModal: this.props.jdChallengeReducer.errorMessages ? true : false
    })
  }

  processSpeciesByFilm = () => {
    this.props.screenLoading(true)
  //  this.props.retrieveSpeciesByFilm(filmId, characterId)
  }

  clearFields = () => {
    this.setState({
        filmId: '',         // 'Select' ??
        characterId: '',    // 'Select' ??
        films: [],
        characters: [],
        result: ''
    })
  }

  handleHide = () => {
      this.props.closeErrorModal();
  }

  handleFilmChange = (e) => {
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
            <Col xs={12}>
                <h4>Show all Species in a given Star Wars film</h4>
            </Col>
          </Row>
        
        <Row>&nbsp;</Row>
        <Row>
          
        </Row>

        <Row>&nbsp;</Row>
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

        <Row>&nbsp;</Row>
        <Row>
          <Col xs={12} mdOffset={4} md={6}>
            <Col xs={6} md={4}>
              <Row bsClass="text-right"><ControlLabel>Result>></ControlLabel></Row>
            </Col>
            <Col xs={6} md={3}>
              <Row>
                <ControlLabel>{this.props.jdChallengeReducer.result}</ControlLabel>
              </Row>
            </Col>
          </Col>
        </Row>
      </Grid>

      <Modal show={this.props.jdChallengeReducer.errorMessages.length > 0}>
        <Modal.Header><Modal.Title>Oops! Error occurred :(</Modal.Title></Modal.Header>
        <Modal.Body>
          <ul>{this.props.jdChallengeReducer.errorMessages.map(err => { return <li>{err}</li> })}</ul>
        </Modal.Body>
        <Modal.Footer><Button onClick={() => this.handleHide()}>Close</Button></Modal.Footer>
      </Modal>
      </div>
    )
  }
}

function formataValor(valor) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(valor); // '$100.00'
}

function mapStateToProps(state) {
    return { 
        jdChallengeReducer: state.JDChallengeReducer
    }
}

const mapDispacthToProps = dispacth => bindActionCreators(actions, dispacth);

export default connect(mapStateToProps, mapDispacthToProps)(JDChallengeComponent)
