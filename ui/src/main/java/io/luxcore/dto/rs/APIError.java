package io.luxcore.dto.rs;

import com.google.api.client.util.Key;

public class APIError {

    @Key("error")
    private String message;

    public APIError() {
    }

    public APIError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "message='" + message + '\'' +
                '}';
    }
}
