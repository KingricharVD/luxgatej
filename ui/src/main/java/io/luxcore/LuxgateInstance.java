package io.luxcore;

import com.google.api.client.http.GenericUrl;

import java.util.Objects;

public class LuxgateInstance {
    private GenericUrl url;
    private String password;

    public GenericUrl getUrl() {
        return url;
    }

    public void setUrl(GenericUrl url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LuxgateInstance that = (LuxgateInstance) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, password);
    }

    @Override
    public String toString() {
        return "LuxgateInstance{" +
                "url=" + url +
                ", password=[SECURED]" +
                '}';
    }
}
