package io.luxcore;

/**
 * Controller that needs clean up itself can implement this
 */
public interface Shutdown {
    void shutdown();
}
