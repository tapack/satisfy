package io.tapack.satisfy.steps.email;

import com.icegreen.greenmail.util.ServerSetup;
import io.tapack.satisfy.email.utils.EmailHelper;
import io.tapack.satisfy.email.utils.MessageBean;
import io.tapack.satisfy.steps.spi.InboxMessagesInspector;
import org.junit.Assert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static io.tapack.satisfy.email.props.PropertyAccessor.getPop3Properties;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class InboxMessagesInspectorPOP3 implements InboxMessagesInspector {
    @Override
    public void verifyInboxContainsMatchingLetter(String sender, String subject, String body) throws IOException, MessagingException {
        List<MessageBean> matchingLetters = EmailHelper.getLettersThatMatchesParameters(sender, subject, body, EmailHelper.getInboxFolder(getPop3Properties()));
        Assert.assertThat(matchingLetters, is(not(empty())));
    }

    @Override
    public void verifyInboxContainsMatchingLetterWithBodyFromFile(String sender, String subject, String filePath) throws IOException, MessagingException {
        List<MessageBean> matchingLetters = EmailHelper.getLettersThatMatchesParameters(sender, subject, EmailHelper.readFileToStringByPathInResources(filePath), EmailHelper.getInboxFolder(getPop3Properties()));
        Assert.assertThat(matchingLetters, is(not(empty())));
    }

    @Override
    public boolean accept(String acceptanceParam) {
        return acceptanceParam.equalsIgnoreCase(ServerSetup.PROTOCOL_POP3);
    }
}