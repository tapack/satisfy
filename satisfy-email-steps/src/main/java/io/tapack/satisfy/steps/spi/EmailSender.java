package io.tapack.satisfy.steps.spi;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSender extends LoadAcceptor {
    void sendEmailToTestBox(String from, String subject, String body) throws MessagingException;

    void sendEmailToTestBoxWithBodyFromFile(String from, String subject, String fileName) throws IOException, MessagingException;
}
