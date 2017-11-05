package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.EmailSender;
import io.tapack.satisfy.steps.spi.EmailSpiFactory;
import io.tapack.satisfy.steps.spi.FakeEmailServerRunner;
import io.tapack.satisfy.steps.spi.InboxMessagesInspector;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import javax.mail.MessagingException;
import java.io.IOException;

public class EmailStepsBDD {

    FakeEmailServerRunner fakeEmailServerRunner = EmailSpiFactory.getFakeEmailServerRunner();
    EmailSender emailSender = EmailSpiFactory.getEmailSender();
    InboxMessagesInspector inboxMessagesInspector = EmailSpiFactory.getInboxMessagesInspector();

    @Given("start fake local mail servers")
    public void startFakeLocalSMTPAndPOP3SSLMailServer() {
        fakeEmailServerRunner.startAllFakeLocalServers();
    }

    @When("'$from' send email to test mailbox with subject '$subject' and body '$body'")
    public void whenUserSendEmailToTestMailboxWithSubjectAndBody(String from, String subject, String body) throws MessagingException {
        emailSender.sendEmailToTestBox(from, subject, body);
    }

    @When("'$from' send email to test mailbox with subject '$subject' and body from file '$fileName'")
    public void whenUserSendEmailToTestMailboxWithSubjectAndBodyFromFile(String from, String subject, String fileName) throws IOException, MessagingException {
        emailSender.sendEmailToTestBoxWithBodyFromFile(from, subject, fileName);
    }

    @Then("verify that inbox contains letter from '$senderEmail' with '$subject' and '$body'")
    public void thenVerifyThatInboxContainLetterFromSenderWithSubjectAndBody(String sender, String subject, String body) throws IOException, MessagingException {
        inboxMessagesInspector.verifyInboxContainsMatchingLetter(sender, subject, body);
    }

    @Then("verify that inbox contains letter from '$senderEmail' with '$subject' and body from file '$fileName'")
    public void thenVerifyThatInboxContainLetterFromSenderWithSubjectAndBodyFromFile(String sender, String subject, String fileName) throws IOException, MessagingException {
        inboxMessagesInspector.verifyInboxContainsMatchingLetterWithBodyFromFile(sender, subject, fileName);
    }
}