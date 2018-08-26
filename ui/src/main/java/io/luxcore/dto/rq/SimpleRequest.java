package io.luxcore.dto.rq;

import com.google.api.client.util.Key;

public class SimpleRequest {

    public SimpleRequest() {
    }

    public SimpleRequest(String password, String method) {
        this.password = password;
        this.method = method;
    }

    @Key("password")
    private String password;

    @Key("method")
    private String method;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "SimpleRequest{" +
                "password='" + password + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
