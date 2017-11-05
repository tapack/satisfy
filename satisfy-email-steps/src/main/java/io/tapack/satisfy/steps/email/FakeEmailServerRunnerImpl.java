package io.tapack.satisfy.steps.email;

import com.icegreen.greenmail.server.AbstractServer;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import io.tapack.satisfy.email.props.PropertyAccessor;
import io.tapack.satisfy.steps.spi.FakeEmailServerRunner;
import net.thucydides.core.Thucydides;
import org.openqa.selenium.net.PortProber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class FakeEmailServerRunnerImpl implements FakeEmailServerRunner {
    private static final Logger LOG = LoggerFactory.getLogger(FakeEmailServerRunnerImpl.class);

    @Override
    public void startAllFakeLocalServers() {
        GreenMail greenMail;
        try {
            greenMail = startGreenMailServers();
        } catch (RuntimeException rex) {
            LOG.debug("TRY TO START FAKE EMAIL SERVERS AGAIN");
            greenMail = startGreenMailServers();
        }
        logFakesStatus(greenMail);
    }

    private GreenMail startGreenMailServers() {
        ServerSetup[] serverSetups = getServerSetups();
        GreenMail greenMail = new GreenMail(serverSetups);
        greenMail.setUser(PropertyAccessor.getTestMailBoxLogin(), PropertyAccessor.getTestMailBoxPass());
        greenMail.start();
        return greenMail;
    }

    @Override
    public boolean accept(String acceptanceParam) {
        return acceptanceParam.equalsIgnoreCase("default");
    }

    private ServerSetup[] getServerSetups() {
        ServerSetup smtpSetup = createSetup(PropertyAccessor.getTestSmtpHost(), PropertyAccessor.getTestSmtpPort(), ServerSetup.PROTOCOL_SMTP);
        ServerSetup smtpsSetup = createSetup(PropertyAccessor.getTestSmtpSslHost(), PropertyAccessor.getTestSmtpSslPort(), ServerSetup.PROTOCOL_SMTPS);
        ServerSetup pop3Setup = createSetup(PropertyAccessor.getTestPop3Host(), PropertyAccessor.getTestPop3Port(), ServerSetup.PROTOCOL_POP3);
        ServerSetup pop3sSetup = createSetup(PropertyAccessor.getTestPop3SslHost(), PropertyAccessor.getTestPop3SslPort(), ServerSetup.PROTOCOL_POP3S);
        ServerSetup imapSetup = createSetup(PropertyAccessor.getTestImapHost(), PropertyAccessor.getTestImapPort(), ServerSetup.PROTOCOL_IMAP);
        ServerSetup imapsSetup = createSetup(PropertyAccessor.getTestImapSslHost(), PropertyAccessor.getTestImapSslPort(), ServerSetup.PROTOCOL_IMAPS);
        return new ServerSetup[]{smtpSetup, smtpsSetup, pop3Setup, pop3sSetup, imapSetup, imapsSetup};
    }

    private ServerSetup createSetup(String host, String port, String serverProtocol) {
        int configuredPort = Integer.parseInt(port);
        int actualPort = getCheckedAvailablePort(configuredPort, serverProtocol);
        if (!(actualPort == configuredPort)) {
            saveActualPortToTestSession(serverProtocol, actualPort);
        }
        return new ServerSetup(actualPort, host, serverProtocol);
    }

    private void saveActualPortToTestSession(String serverProtocol, int actualPort) {
        String portKey = PropertyAccessor.getTestPortPropertyKey(serverProtocol);
        Thucydides.getCurrentSession().put(portKey, String.valueOf(actualPort));
        LOG.debug("ADDED TO SESSION IS " + portKey + " = " + Thucydides.getCurrentSession().get(portKey));
    }

    private int getCheckedAvailablePort(int configuredPort, String serverProtocol) {
        int actualPort;
        if (isAvailablePort(configuredPort)) {
            actualPort = configuredPort;
        } else {
            actualPort = PortProber.findFreePort();
            LOG.debug("Configured or default port = " + configuredPort + " for" + serverProtocol.toUpperCase() + " server is not available!!!");
            LOG.debug("Founded free port for " + serverProtocol.toUpperCase() + " is " + actualPort);
        }
        return actualPort;
    }

    private boolean isAvailablePort(int port) {
        ServerSocket socket;
        try {
            socket = new ServerSocket();
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress("localhost", port));
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void logFakesStatus(GreenMail greenMail) {
        LOG.debug("----------------FAKE SERVERS STATUS---------------------");
        logStatus(greenMail.getSmtp());
        logStatus(greenMail.getSmtps());
        logStatus(greenMail.getPop3());
        logStatus(greenMail.getPop3s());
        logStatus(greenMail.getImap());
        logStatus(greenMail.getImaps());
        LOG.debug("--------------------------------------------------------");
    }

    private void logStatus(AbstractServer server) {
        LOG.debug(server.getProtocol().toUpperCase() + " ---" + getStatus(server.isRunning()) + " ON HOST <" + server.getBindTo() + "> AND PORT <" + server.getPort() + ">");
    }

    private String getStatus(boolean isRunning) {
        return isRunning ? " < IS RUNNING >" : " < IS STOPPED >";
    }
}