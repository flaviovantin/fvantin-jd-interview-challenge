import React, { Component } from 'react'
import { Grid, Row } from  'react-bootstrap'

class Home extends Component {

  render() {
    return (
      <div>
        <Grid>
          <h4>Welcome!</h4>
          <Row>&nbsp;</Row>
          <h5>Please, read the following considerations.</h5>
          <Row>&nbsp;</Row>

          <ul>
            <li>Não há uma seleção dos Hubs de origem e destino. Isso poderia ser feito através de consultas por CEP na API dos Correios ou apresentando um mapa do Google Maps embutido na tela de cálculo de frete. Uma vez obtidas as coordenadas (Lat/Lng), poderia ser utilizado o Google Cloud Platform de forma a traçar rotas ou estipular distâncias. <br/>Para esta solução, considere a distância entre os Hubs fornecendo uma quilometragem total, por exemplo, 340 Km.</li>
            <li>Para os valores de pedágio, considere um valor total para o trajeto percorrido, por exemplo: 450.50 (ponto). Este valor não é mandatório para o cáculo.</li>
            <li>O cálculo é efetuado com base nas tabelas de frete fornecidas pela ANTT. As cinco tabelas estão cadastradas na base de dados (NoSQL), e podem ser visualizadas através do menu <em>Tabelas de Frete ANTT</em>.</li>
          </ul>

          <h5>Sobre a arquitetura e tecnologias utiliadas nesta solução:</h5>
          <ul>
            <li>Este sistema está totalmente hospedado na infraestrutura de cloud da AWS, Amazon Web Services. Basicamente, ele faz uso de três de seus serviços:
              <ul>
                <li>API Gateway - usado para gerenciamento e exposição da API. Faz a integração entre frontend e backend (Lambdas)</li>
                <li>Lambda - aplicação Java servless. Suporta um número ilimitado de request de forma elástica - para cada resquest, um novo Lambda é executado.</li>
                <li>DynamoDB - base de dados NoSQL</li>
                <li>Frontend: aplicação feita em ReactJS (Redux), ES6, React-bootstrap</li>
              </ul>
            </li>
            <li><u>O primeiro acesso feito em cada Lambda levará em torno de 10 segundos para ser executado.</u> Isso se deve ao fato deste recurso não estar em uso (lambda não "aquecido"), no entanto uma vez executado, a resposta é muito rápida. Nesta implementação, existem dois lambdas: um para a listagem da tabela de fretes, outra para a execução do cálculo. <br/>O função Lambda se mantem em execução por aproximadamente de 3 a 15 minutos. Para esta solução, <u>não</u> foi considerado um mecanismo de keep alive.</li>
            <li><u>Esta arquitetura é em torno de 14 vezes mais barata do que uma estrutura mantida de maneira tradicional</u>, como no caso de uma instancia EC2 (para execução do Java) e uma base de dados SQL (por exemplo, MySQL). Esta economia ocorre porque os serviços não são mantidos em execução e por ser tratar de uma arquitetura servless.</li>
            <li>O sistema não possue autenticação; estará com acesso liberado apenas por alguns dias. Caso esteja sem acesso, por favor, entre em contato através do menu <em>Sobre o Candidato</em>.</li>
            <li><strong>NOTA:</strong> Trata-se de uma conta AWS pessoal, por favor, <strong>não efetuar testes de carga nos endpoints</strong> (backend / API Gateway).</li>
          </ul>
        </Grid>
      </div>
    )
  }
}

export default Home