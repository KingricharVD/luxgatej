package io.luxcore;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.luxcore.dto.LuxgateResponse;
import io.luxcore.dto.rq.SimpleRequest;
import io.luxcore.dto.rq.UnauthRequest;
import io.luxcore.dto.rs.APIError;
import io.luxcore.dto.rs.Asset;
import io.luxcore.dto.rs.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Luxgate daemon HTTP Endpoint
 */
@Singleton
public class LuxgateHttpService implements LuxgateService {


    @Inject
    private LuxgateInstance instance;

    private static final Logger logger = LoggerFactory.getLogger(LuxgateHttpService.class);

    private HttpTransport httpTransport = new NetHttpTransport();
    private JsonFactory jsonFactory = new JacksonFactory();

    private final HttpRequestFactory requestFactory;
    private RequestFactory rqFactory;

    public LuxgateHttpService() {

        logger.info("Initialize");

        requestFactory = httpTransport.createRequestFactory(request -> {
            request.setParser(new JsonObjectParser(jsonFactory));
        });

        rqFactory = new RequestFactory(requestFactory, jsonFactory);
    }

    @Override
    public StatusResponse status() {
        return rqFactory
                .post(instance.getUrl(), new UnauthRequest("status"), StatusResponse.class)
                .getSuccessOrThrow();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Asset> listAssets() {
        return rqFactory.post(instance.getUrl(),
                new SimpleRequest(instance.getPassword(), "listassets"), List.class).getSuccessOrThrow();
    }

    @Override
    public boolean login() {
        // check password validity with listAssets method
        return !rqFactory.post(instance.getUrl(),
                new SimpleRequest(instance.getPassword(), "listassets"), List.class).isError();
    }

    private static class RequestFactory {

        private HttpRequestFactory httpRequestFactory;
        private JsonFactory jsonFactory;

        public RequestFactory(HttpRequestFactory httpFactory, JsonFactory jsonFactory) {
            this.httpRequestFactory = httpFactory;
            this.jsonFactory = jsonFactory;
        }


        public <T> LuxgateResponse<T> post(GenericUrl url, Object data, Class<T> clazz) {
            HttpRequest httpRequest = null;
            HttpResponse response = null;
            try {
                httpRequest = httpRequestFactory.buildPostRequest(url, new JsonHttpContent(jsonFactory, data));
                response = httpRequest.execute();
                try {
                    T payload = response.parseAs(clazz);
                    return new LuxgateResponse<>(payload);
                } catch (IllegalArgumentException e) {
                    try {
                        if (response != null) {
                            APIError error = response.parseAs(APIError.class);
                            logger.info("API error: {}", error);
                            return new LuxgateResponse<>(error);
                        }
                        throw new RuntimeException("Responce is null", e);
                    } catch (Exception ex) {
                        if (ex instanceof IOException) {
                            IOException cause = (IOException) ex;
                            return new LuxgateResponse<>(new APIError(cause.getMessage()));
                        }
                        throw ex;
                    }
                }
            } catch (IOException e) {
                logger.error("Error while building request: "  + httpRequest, e);
                throw new RuntimeException(e);
            }
        }
    }
}
