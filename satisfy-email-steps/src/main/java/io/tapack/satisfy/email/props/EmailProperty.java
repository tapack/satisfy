package io.tapack.satisfy.email.props;

import com.icegreen.greenmail.util.ServerSetupTest;

import static io.tapack.satisfy.email.props.PropertyAccessor.DEFAULT_SSL_SOCKET_FACTORY;
import static io.tapack.satisfy.email.props.PropertyAccessor.LOCAL_HOST;

enum EmailProperty {
    // Configurable testing properties for users
    TEST_SMTP_HOST(LOCAL_HOST),
    TEST_SMTP_PORT(ServerSetupTest.SMTP.getPort()),
    TEST_SMTPSSL_HOST(LOCAL_HOST),
    TEST_SMTPSSL_PORT(ServerSetupTest.SMTPS.getPort()),
    TEST_POP3_HOST(LOCAL_HOST),
    TEST_POP3_PORT(ServerSetupTest.POP3.getPort()),
    TEST_POP3SSL_HOST(LOCAL_HOST),
    TEST_POP3SSL_PORT(ServerSetupTest.POP3S.getPort()),
    TEST_IMAP_HOST(LOCAL_HOST),
    TEST_IMAP_PORT(ServerSetupTest.IMAP.getPort()),
    TEST_IMAPSSL_HOST(LOCAL_HOST),
    TEST_IMAPSSL_PORT(ServerSetupTest.IMAPS.getPort()),
    TEST_MAILBOX_LOGIN("testingbox@tapack.io"),
    TEST_MAILBOX_PASSWORD("Testing77"),
    TEST_SEND_PROTOCOL("smtps"),
    TEST_SMTP_SERVER_LOGIN("tapack4sender"),
    TEST_SMTP_SERVER_PASSWORD("tapack4sender"),
    TEST_USE_FAKE_SERVERS("default"),
    TEST_RECEIVE_PROTOCOL("pop3s"),
    TEST_SSL_SOCKETFACTORY(DEFAULT_SSL_SOCKET_FACTORY),
    //Email Session Configuration properties
    MAIL_SMTP_HOST,
    MAIL_SMTP_PORT,
    MAIL_STORE_PROTOCOL,
    MAIL_TRANSPORT_PROTOCOL,

    MAIL_POP3_HOST,
    MAIL_POP3_PORT,
    MAIL_POP3_AUTH,

    MAIL_SMTPS_HOST,
    MAIL_SMTPS_PORT,
    MAIL_SMTPS_AUTH,
    MAIL_SMTPS_SSL_TRUST,
    MAIL_SMTPS_SOCKETFACTORY_PORT,
    MAIL_SMTPS_SSL_SOCKETFACTORY_CLASS,

    MAIL_POP3S_HOST,
    MAIL_POP3S_AUTH,
    MAIL_POP3S_PORT,
    MAIL_POP3S_SOCKETFACTORY_CLASS,
    MAIL_POP3S_SSL_TRUST("*"),
    MAIL_POP3S_SOCKETFACTORY_PORT,
    MAIL_POP3S_SOCKETFACTORY_FALLBACK,

    MAIL_IMAP_HOST,
    MAIL_IMAP_PORT,
    MAIL_IMAP_AUTH,

    MAIL_IMAPS_HOST,
    MAIL_IMAPS_PORT,
    MAIL_IMAPS_AUTH,
    MAIL_IMAPS_SOCKETFACTORY_CLASS,
    MAIL_IMAPS_SOCKETFACTORY_PORT;

    EmailProperty() {
    }

    EmailProperty(String def) {
        this.def = def;
    }

    EmailProperty(int intDef) {
        this.def = String.valueOf(intDef);
    }

    private String def;

    @Override
    public String toString() {
        return isSocketFactory() ? makeSocketFactoryParamKey() : makeParamKey();
    }

    private boolean isSocketFactory() {
        return this.name().contains("SOCKETFACTORY");
    }

    private String makeParamKey() {
        return super.toString().toLowerCase().replaceAll("_", ".");
    }

    private String makeSocketFactoryParamKey() {
        return makeParamKey().replace("socketfactory", "socketFactory");
    }

    public String getDefault() {
        if (def == null)
            def = "";
        return def;
    }
}