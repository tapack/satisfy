package io.tapack.satisfy.steps.helper;

import com.google.inject.Inject;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

public class WebServiceProperties {
    private final EnvironmentVariables environmentVariables;

    private static WebServiceProperties webServiceProperties = new WebServiceProperties();

    public static WebServiceProperties getWebServiceProperty() {
        return webServiceProperties;
    }

    public WebServiceProperties() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class));
    }

    @Inject
    public WebServiceProperties(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public String getProperty(String name) {
        return environmentVariables.getProperty(name);
    }

}
