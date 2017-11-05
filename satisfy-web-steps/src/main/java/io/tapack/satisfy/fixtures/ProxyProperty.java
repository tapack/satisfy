package io.tapack.satisfy.fixtures;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public enum ProxyProperty {

    ZAP_PROXY_HOST, ZAP_PROXY_PORT;

    private String propertyName;

    ProxyProperty() {
        this.propertyName = name().replaceAll("_", ".").toLowerCase();
    }

    ProxyProperty(final String propertyName) {
        this.propertyName = propertyName;
    }

    public String from(Properties properties) {
        if (properties.getProperty(getPropertyName()) == null) {
            return "";
        }
        return properties.getProperty(getPropertyName());
    }

    public String from(Properties properties, String defaultValue) {
        String value = properties.getProperty(getPropertyName());
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
