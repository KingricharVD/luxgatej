package io.luxcore.dto;

import io.luxcore.dto.rs.APIError;

public class LuxgateResponse<T> {

    private APIError error = null;
    private T payload = null;

    public LuxgateResponse(T payload) {
        this.payload = payload;
    }

    public LuxgateResponse(APIError error) {
        this.error = error;
    }

    public void emit(ResponseConsumer<T> successConsumer,
                     ResponseConsumer<APIError> errorConsumer) {
        if (errorConsumer != null) {
            errorConsumer.consume(error);
        } else if (payload != null) {
            successConsumer.consume(payload);
        } else {
            throw new IllegalStateException("No error or payload in response" + this);
        }
    }

    public boolean isError() {
        return error != null;
    }

    public T getSuccessOrThrow() {
        if (payload == null) {
            throw new RuntimeException("Error:" + error);
        }

        return payload;
    }

    @Override
    public String toString() {
        return "LuxgateResponse{" +
                "error=" + error +
                ", payload=" + payload +
                '}';
    }
}
