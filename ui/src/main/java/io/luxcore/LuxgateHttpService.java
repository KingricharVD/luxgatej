package io.luxcore;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.Singleton;
import io.luxcore.dto.rq.UnauthRequest;
import io.luxcore.dto.rs.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Luxgate daemon HTTP Endpoint
 */
@Singleton
public class LuxgateHttpService implements LuxgateService {


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
    public StatusResponse status(LuxgateInstance instance) {
        return rqFactory.post(instance.getUrl(), new UnauthRequest("status"), StatusResponse.class);
    }

    private static class RequestFactory {


        private HttpRequestFactory httpRequestFactory;
        private JsonFactory jsonFactory;
        public RequestFactory(HttpRequestFactory httpFactory, JsonFactory jsonFactory) {
            this.httpRequestFactory = httpFactory;
            this.jsonFactory = jsonFactory;
        }


        public <T> T post(GenericUrl url, Object data, Class<T> clazz) {
            HttpRequest httpRequest = null;

            try {
                httpRequest = httpRequestFactory.buildPostRequest(url, new JsonHttpContent(jsonFactory, data));
                HttpResponse response = httpRequest.execute();
                return response.parseAs(clazz);
            } catch (IOException e) {
                logger.error("Error while building request: "  + httpRequest, e);
                throw new RuntimeException(e);
            }
        }
    }
}
