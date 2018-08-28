package io.luxcore.cliargs;

import com.beust.jcommander.Parameter;

public class CommandLineArgs {

    @Parameter(
            names = {"--url", "-u"},
            description = "Luxgate URL (http://<host>:<port>)",
            validateValueWith = URLValidator.class

    )
    private String luxgateURL;

    @Parameter
    private String luxgatePath;
}
