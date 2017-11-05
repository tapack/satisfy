package io.tapack.satisfy.email.props;

import com.icegreen.greenmail.util.ServerSetup;
import net.thucydides.core.Thucydides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class PropertyAccessor {
    private static final EnvironmentProperties ENVIRONMENT_PROPERTIES = new EnvironmentProperties();
    public static final String LOCAL_HOST = "127.0.0.1";
    public static final String DEFAULT_SSL_SOCKET_FACTORY = "com.icegreen.greenmail.util.DummySSLSocketFactory";
    private static final Logger LOG = LoggerFactory.getLogger(PropertyAccessor.class);

    private PropertyAccessor() {
    }

    public static Properties getPop3SslProperties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_STORE_PROTOCOL, ServerSetup.PROTOCOL_POP3S);
        props.set(EmailProperty.MAIL_POP3S_HOST, getTestPop3SslHost());
        props.set(EmailProperty.MAIL_POP3S_PORT, getTestPop3SslPort());
        props.set(EmailProperty.MAIL_POP3S_SOCKETFACTORY_CLASS, getSslSocketFactory());
        props.set(EmailProperty.MAIL_POP3S_SOCKETFACTORY_PORT, getTestPop3SslPort());
        props.set(EmailProperty.MAIL_POP3S_SOCKETFACTORY_FALLBACK, false);
        props.setDefault(EmailProperty.MAIL_POP3S_SSL_TRUST);
        props.set(EmailProperty.MAIL_POP3S_AUTH, true);
        return props;
    }

    public static Properties getPop3Properties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_STORE_PROTOCOL, ServerSetup.PROTOCOL_POP3);
        props.set(EmailProperty.MAIL_POP3_HOST, getTestPop3Host());
        props.set(EmailProperty.MAIL_POP3_PORT, getTestPop3Port());
        props.set(EmailProperty.MAIL_POP3_AUTH, true);
        return props;
    }

    public static Properties getImapProperties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_STORE_PROTOCOL, ServerSetup.PROTOCOL_IMAP);
        props.set(EmailProperty.MAIL_IMAP_HOST, getTestImapHost());
        props.set(EmailProperty.MAIL_IMAP_PORT, getTestImapPort());
        props.set(EmailProperty.MAIL_IMAP_AUTH, true);
        return props;
    }

    public static Properties getImapSslProperties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_STORE_PROTOCOL, ServerSetup.PROTOCOL_IMAPS);
        props.set(EmailProperty.MAIL_IMAPS_HOST, getTestImapSslHost());
        props.set(EmailProperty.MAIL_IMAPS_PORT, getTestImapSslPort());
        props.set(EmailProperty.MAIL_IMAPS_AUTH, true);
        props.set(EmailProperty.MAIL_IMAPS_SOCKETFACTORY_CLASS, getSslSocketFactory());
        props.set(EmailProperty.MAIL_IMAPS_SOCKETFACTORY_PORT, getTestImapSslPort());
        return props;
    }

    public static Properties getSmtpSslProperties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_TRANSPORT_PROTOCOL, ServerSetup.PROTOCOL_SMTPS);
        props.set(EmailProperty.MAIL_SMTPS_HOST, getTestSmtpSslHost());
        props.set(EmailProperty.MAIL_SMTPS_PORT, getTestSmtpSslPort());
        props.set(EmailProperty.MAIL_SMTPS_AUTH, true);
        props.set(EmailProperty.MAIL_SMTPS_SSL_SOCKETFACTORY_CLASS, getSslSocketFactory());
        props.set(EmailProperty.MAIL_SMTPS_SOCKETFACTORY_PORT, getTestSmtpSslPort());
        props.set(EmailProperty.MAIL_SMTPS_SSL_TRUST, getTestSmtpSslHost());
        return props;
    }

    public static Properties getSmtpProperties() {
        SimplifiedProperties props = new SimplifiedProperties();
        props.set(EmailProperty.MAIL_TRANSPORT_PROTOCOL, ServerSetup.PROTOCOL_SMTP);
        props.set(EmailProperty.MAIL_SMTP_HOST, getTestSmtpHost());
        props.set(EmailProperty.MAIL_SMTP_PORT, getTestSmtpPort());
        return props;
    }

    public static String getTestSmtpHost() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTP_HOST);
    }

    public static String getTestSmtpPort() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTP_PORT);
    }

    public static String getTestPop3Host() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_POP3_HOST);
    }

    public static String getTestPop3Port() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_POP3_PORT);
    }

    public static String getTestSmtpSslHost() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTPSSL_HOST);
    }

    public static String getTestSmtpSslPort() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTPSSL_PORT);
    }

    public static String getTestPop3SslHost() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_POP3SSL_HOST);
    }

    public static String getTestPop3SslPort() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_POP3SSL_PORT);
    }

    public static String getTestImapHost() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_IMAP_HOST);
    }

    public static String getTestImapPort() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_IMAP_PORT);
    }

    public static String getTestImapSslHost() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_IMAPSSL_HOST);
    }

    public static String getTestImapSslPort() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_IMAPSSL_PORT);
    }

    public static String getTestMailBoxLogin() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_MAILBOX_LOGIN);
    }

    public static String getTestMailBoxPass() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_MAILBOX_PASSWORD);
    }

    public static String getTestSmtpLogin() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTP_SERVER_LOGIN);
    }

    public static String getTestSmtpPass() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SMTP_SERVER_PASSWORD);
    }

    public static String getSendingProtocol() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SEND_PROTOCOL);
    }

    public static void setSendingProtocol(String protocol) {
        setProperty(EmailProperty.TEST_SEND_PROTOCOL, protocol);
    }

    public static String getUseFakeProperty() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_USE_FAKE_SERVERS);
    }

    public static String getReceiveProtocol() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_RECEIVE_PROTOCOL);
    }

    public static void setReceiveProtocol(String protocol) {
        setProperty(EmailProperty.TEST_RECEIVE_PROTOCOL, protocol);
    }

    public static String getTestPortPropertyKey(String serverProtocol) {
        for (EmailProperty property : EmailProperty.values()) {
            String propertyKey = property.toString();
            if (propertyKey.contains("test") && propertyKey.contains(serverProtocol.toLowerCase()) && propertyKey.contains("port")) {
                return propertyKey;
            }
        }
        return "";
    }

    private static String getSslSocketFactory() {
        return getPropertyByPriorityFromSessionToDefault(EmailProperty.TEST_SSL_SOCKETFACTORY);
    }

    private static String getPropertyByPriorityFromSessionToDefault(EmailProperty testingEmailProperty) {
        String actualProperty;
        if (currentSessionContainsValueForProperty(testingEmailProperty)) {
            actualProperty = (String) Thucydides.getCurrentSession().get(testingEmailProperty.toString());
        } else {
            actualProperty = loadConfiguredOrDefaultProperty(testingEmailProperty);
        }
        LOG.debug("Property <=" + testingEmailProperty.toString() + "=> was loaded with value <=" + actualProperty + "=>");
        return actualProperty;
    }

    private static String loadConfiguredOrDefaultProperty(EmailProperty testingEmailProperty) {
        String property = ENVIRONMENT_PROPERTIES.getPropertyWithName(testingEmailProperty.toString());
        return property.isEmpty() ? testingEmailProperty.getDefault() : property;
    }

    private static boolean currentSessionContainsValueForProperty(EmailProperty testingEmailProperty) {
        return Thucydides.getCurrentSession().containsKey(testingEmailProperty.toString());
    }

    private static void setProperty(EmailProperty emailProperty, String value) {
        ENVIRONMENT_PROPERTIES.setProperty(emailProperty, value);
    }
}