package io.tapack.satisfy.steps.email;

import com.icegreen.greenmail.util.ServerSetup;
import io.tapack.satisfy.email.utils.EmailHelper;
import io.tapack.satisfy.email.utils.MessageBean;
import io.tapack.satisfy.steps.spi.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;

import static io.tapack.satisfy.email.props.PropertyAccessor.getSmtpSslProperties;
import static io.tapack.satisfy.email.props.PropertyAccessor.getTestMailBoxLogin;

public class EmailSenderSMTPS implements EmailSender {

    @Override
    public void sendEmailToTestBox(String from, String subject, String body) throws MessagingException {
        MessageBean messageBean = new MessageBean.Builder().from(from).to(getTestMailBoxLogin()).subject(subject).content(body).build();
        EmailHelper.sendPlainMessage(messageBean, getSmtpSslProperties());
    }

    @Override
    public void sendEmailToTestBoxWithBodyFromFile(String from, String subject, String fileName) throws IOException, MessagingException {
        EmailHelper.sendFileMessage(from, subject, fileName, getSmtpSslProperties());
    }

    @Override
    public boolean accept(String acceptanceParam) {
        return acceptanceParam.equalsIgnoreCase(ServerSetup.PROTOCOL_SMTPS);
    }
}