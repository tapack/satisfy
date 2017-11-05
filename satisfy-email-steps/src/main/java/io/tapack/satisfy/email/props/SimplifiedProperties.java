package io.tapack.satisfy.email.props;

import java.util.Properties;

class SimplifiedProperties extends Properties {
    public void set(EmailProperty emailProperty, String value) {
        super.setProperty(emailProperty.toString(), value);
    }

    public void setDefault(EmailProperty emailProperty) {
        super.setProperty(emailProperty.toString(), emailProperty.getDefault());
    }

    public void set(EmailProperty emailProperty, boolean value) {
        set(emailProperty, String.valueOf(value));
    }
}