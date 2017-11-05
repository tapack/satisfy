package io.tapack.satisfy.email.props;

import com.google.inject.Inject;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import static org.apache.commons.lang.StringUtils.isEmpty;

class EnvironmentProperties {
    private final EnvironmentVariables environmentVariables;

    public EnvironmentProperties() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class));
    }

    @Inject
    public EnvironmentProperties(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public String getPropertyWithName(String name) {
        if (isDefined(name)) {
            return environmentVariables.getProperty(name);
        } else {
            return "";
        }
    }

    private boolean isDefined(String name) {
        return !isEmpty(environmentVariables.getProperty(name));
    }

    public void setProperty(EmailProperty emailProperty, String value) {
        environmentVariables.setProperty(emailProperty.toString(), value.trim());
    }
}