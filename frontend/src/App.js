import React, { Component } from 'react'
import { Navbar, Nav, NavItem, NavDropdown, MenuItem, Modal, Button } from 'react-bootstrap'
import { Route, Link } from 'react-router-dom'
import Home from './components/Home'
import JDChallengeComponent from './components/api/JDChallengeComponent'
import AboutComponent from './components/candidate/AboutComponent'

class App extends Component {

  constructor (props) {
    super(props)
    this.handleHide = this.handleHide.bind(this)
  }

  handleHide() {
    this.setState({ showModal: false })
  }

  render() {
    return (
      <div>
        <Navbar inverse collapseOnSelect>
          <Navbar.Header>
            <Navbar.Brand>
              <Link to="/">Interview Test - SWAPI.co</Link>
            </Navbar.Brand>
            <Navbar.Toggle />
          </Navbar.Header>
          <Navbar.Collapse>
            <Nav>
              <NavItem eventKey={2}><Link to="/speciesandfilms">Search Species & Films</Link></NavItem>
              <NavItem eventKey={32}><Link to="/aboutcandidate">Candidate Infos</Link></NavItem>
            </Nav>
          </Navbar.Collapse>
        </Navbar>

        <Route path="/" exact={true} component={Home} />
        <Route path="/speciesandfilms" component={JDChallengeComponent} />
        <Route path="/aboutcandidate" component={AboutComponent} />
      </div>
    )
  }
}

export default App
