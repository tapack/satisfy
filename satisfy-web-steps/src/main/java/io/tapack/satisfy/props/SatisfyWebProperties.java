package io.tapack.satisfy.props;

import com.google.inject.Inject;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class SatisfyWebProperties {

    public static final String DEFAULT_SATISFY_WAIT = "30";
    private static SatisfyWebProperties satisfyWebProperties = new
            SatisfyWebProperties();
    private final EnvironmentVariables environmentVariables;

    public SatisfyWebProperties() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class));
    }

    @Inject
    public SatisfyWebProperties(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public static SatisfyWebProperties getSatisfyWebProperties() {
        return satisfyWebProperties;
    }

    public boolean isDefined(WebProperty name) {
        return !isEmpty(environmentVariables.getProperty(name));
    }

    public String getProperty(WebProperty name) {
        return environmentVariables.getProperty(name);
    }

    public int getSatisfyWait() {
        return Integer.parseInt(environmentVariables.getProperty(WebProperty
                .SATISFY_TIMEOUT, DEFAULT_SATISFY_WAIT));
    }

}