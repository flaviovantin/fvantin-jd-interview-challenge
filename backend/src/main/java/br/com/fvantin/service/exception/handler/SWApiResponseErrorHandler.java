package br.com.fvantin.service.exception.handler;

import br.com.fvantin.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class SWApiResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        switch (httpResponse.getStatusCode()) {
            case NOT_FOUND:
                throw new ResourceNotFoundException();
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException();
            case BAD_REQUEST:
                throw new BadRequestException();
            case SERVICE_UNAVAILABLE:
                throw new ServiceUnavailableException();
            case GATEWAY_TIMEOUT:
                throw new GatewayTimeout();
            case BAD_GATEWAY:
                throw new BadGatewayException();
            default:
                throw new RuntimeException("Unknown error from swapi.co :[");
        }

//         if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
//            //Handle CLIENT_ERROR
//            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
//
//            }
//        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
//             //Handle SERVER_ERROR
////             HttpServerErrorException
//             throw new HttpClientErrorException(httpResponse.getStatusCode());
//
//         }
    }
}
