package io.luxcore.dto;

public interface ResponseConsumer<T> {
    void consume(T input);
}
