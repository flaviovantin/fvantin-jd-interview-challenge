package br.com.fvantin.service.exception.handler;

import br.com.fvantin.service.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
            // 400 series
            case BAD_REQUEST:
                throw new BadRequestException();
            case UNAUTHORIZED:
                throw new UnauthorizedException();
            case FORBIDDEN:
                throw new ForbiddenException();
            case NOT_FOUND:
                throw new ResourceNotFoundException();
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException();
            case REQUEST_TIMEOUT:
                throw new RequestTimeoutException();
            case TOO_MANY_REQUESTS:
                throw new TooManyRequestsException();
            // 500 series
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException();
            case BAD_GATEWAY:
                throw new BadGatewayException();
            case SERVICE_UNAVAILABLE:
                throw new ServiceUnavailableException();
            case GATEWAY_TIMEOUT:
                throw new GatewayTimeoutException();
            default:
                throw new RuntimeException("Stranger Things occurred... :o");
        }
    }
}
