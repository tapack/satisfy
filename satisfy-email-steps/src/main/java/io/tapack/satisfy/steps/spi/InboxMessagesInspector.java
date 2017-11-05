package io.tapack.satisfy.steps.spi;

import javax.mail.MessagingException;
import java.io.IOException;

public interface InboxMessagesInspector extends LoadAcceptor {
    void verifyInboxContainsMatchingLetter(String sender, String subject, String body) throws IOException, MessagingException;

    void verifyInboxContainsMatchingLetterWithBodyFromFile(String sender, String subject, String filePath) throws IOException, MessagingException;
}