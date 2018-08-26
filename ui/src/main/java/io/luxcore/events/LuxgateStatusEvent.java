package io.luxcore.events;

import java.util.Objects;

/**
 * Luxgate target instance status event updated
 */
public class LuxgateStatusEvent {

    private boolean online;

    public LuxgateStatusEvent(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LuxgateStatusEvent that = (LuxgateStatusEvent) o;
        return online == that.online;
    }

    @Override
    public int hashCode() {
        return Objects.hash(online);
    }

    @Override
    public String toString() {
        return "LuxgateStatusEvent{" +
                "online=" + online +
                '}';
    }
}
