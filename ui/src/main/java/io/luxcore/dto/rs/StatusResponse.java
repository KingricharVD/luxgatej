package io.luxcore.dto.rs;

import com.google.api.client.util.Key;

/**
 * Without password
 */
public class StatusResponse {

    @Key("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "result='" + result + '\'' +
                '}';
    }
}
