package io.luxcore.dto.rq;

import com.google.api.client.util.Key;

public class UnauthRequest {

    public UnauthRequest(String method) {
        this.method = method;
    }

    @Key("method")
    private String method;
}
