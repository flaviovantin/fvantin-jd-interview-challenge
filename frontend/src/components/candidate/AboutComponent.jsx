import React, { Component } from 'react'
import { Grid, Row, Col } from  'react-bootstrap'

class AboutComponent extends Component {

  render() {
    return (
      <div>
        <Grid>
          <Col xs={6} mdOffset={2} md={7}>
            <Row>&nbsp;</Row>
            <Row bsClass="text-center"><h4>Flávio Vantin</h4></Row>
            <Row>&nbsp;</Row>
            <ul>
              <li><h5><a target="_blank" href={"https://www.linkedin.com/in/vantin"} style={{cursor: "pointer", color: "dimgray"}}>LinkedIn Profile</a></h5></li>
              <li><h5>Email: vantin@gmail.com</h5></li>
              <li><h5>Cell: (19) 99301-8300 (WhatsApp)</h5></li>
            </ul>
          </Col>
        </Grid>
      </div>
    )
  }
}

export default AboutComponent