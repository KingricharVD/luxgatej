package io.luxcore.cliargs;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class URLValidator implements IValueValidator<String> {

    private static final Logger logger = LoggerFactory.getLogger(URLValidator.class);

    @Override
    public void validate(String name, String value) throws ParameterException {

        logger.debug("Validating {} {}", name, value);

        try {
            URL url = new URL(value);
            if (!url.getProtocol().startsWith("http")) {
                throw new ParameterException("Unknown protocol. Supported only http(s)");
            }
        } catch (MalformedURLException e) {
            throw new ParameterException(e);
        }
    }
}
