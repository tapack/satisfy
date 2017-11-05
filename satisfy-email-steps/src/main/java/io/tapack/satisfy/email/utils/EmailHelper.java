package io.tapack.satisfy.email.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static io.tapack.satisfy.email.props.PropertyAccessor.*;
import static org.apache.commons.io.FileUtils.readFileToString;


public class EmailHelper {

    private EmailHelper() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailHelper.class);
    private static final String TEXT_MIME_TYPE = "text/*";
    private static final String MULTIPART_MIME_TYPE = "multipart/*";

    public static void sendPlainMessage(MessageBean messageBean, Properties properties) throws MessagingException {
        sendMessage(messageBean, "plaintext", properties);
    }

    public static void sendFileMessage(String from, String subject, String fileName, Properties properties) throws MessagingException, IOException {
        MessageBean messageBean = new MessageBean.Builder().from(from).to(getTestMailBoxLogin()).subject(subject).
                content(readFileToStringByPathInResources(fileName)).build();
        sendMessage(messageBean, FilenameUtils.getExtension(fileName), properties);

    }

    public static Folder getInboxFolder(Properties sessionProperties) throws MessagingException {
        Session emailSession = Session.getInstance(sessionProperties);
        Store emailStore = emailSession.getStore();
        emailStore.connect(getTestMailBoxLogin(), getTestMailBoxPass());
        return emailStore.getFolder("INBOX");
    }

    public static String readFileToStringByPathInResources(String filePath) throws IOException {
        return readFileToString(new File(EmailHelper.class.getResource(filePath).getFile()), Charset.defaultCharset());
    }

    public static List<MessageBean> getLettersThatMatchesParameters(String sender, String subject,
                                                                    String body, Folder inbox) throws MessagingException, IOException {
        List<MessageBean> filteredMessages = Collections.emptyList();
        try {
            inbox.open(Folder.READ_ONLY);
            filteredMessages = filterMessages(sender, subject, body, inbox);
        } finally {
            inbox.close(false);
            inbox.getStore().close();
        }
        return filteredMessages;
    }

    private static void sendMessage(MessageBean msgBean, String mimeSubtype, Properties sendingProperties)
            throws MessagingException {
        Session session = Session.getInstance(sendingProperties);
        MimeMessage msg = createMimeMessage(msgBean, mimeSubtype, session);
        send(getTestSmtpLogin(), getTestSmtpPass(), msg, session.getTransport());
        LOGGER.debug("Email was sent to: " + msgBean.getTo());
    }

    private static void send(String login, String password, MimeMessage msg, Transport sessionTransport) throws MessagingException {
        try {
            sessionTransport.connect(login, password);
            sessionTransport.sendMessage(msg, msg.getAllRecipients());
        } finally {
            sessionTransport.close();
        }
    }

    private static MimeMessage createMimeMessage(MessageBean msgBean, String mimeSubtype, Session session) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(msgBean.getFrom()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(msgBean.getTo()));
        msg.setSubject(msgBean.getSubject());
        msg.setText(msgBean.getContent(), Charset.defaultCharset().name(), mimeSubtype);
        return msg;
    }

    private static MessageBean getPart(MimeMessage message)
            throws MessagingException, IOException {
        if (message.isMimeType(TEXT_MIME_TYPE)) {
            return createMessageBeanFromMessage(message, IOUtils.toString(message.getInputStream(), Charset.defaultCharset()).trim(), null);
        } else if (message.isMimeType(MULTIPART_MIME_TYPE)) {
            return getMessageBeanFromMultiPartMsg(message);
        } else {
            throw new MessagingException();
        }
    }

    private static MessageBean createMessageBeanFromMessage(Message message, String content, List<File> attachments) throws MessagingException, IOException {
        return new MessageBean.Builder()
                .msgId(message.getMessageNumber())
                .subject(MimeUtility.decodeText(message.getSubject()))
                .from(message.getFrom()[0].toString())
                .to("").dateSent("").setNew(false)
                .content(content).attachments(attachments).build();
    }

    private static MessageBean getMessageBeanFromMultiPartMsg(Message message)
            throws IOException, MessagingException {
        ArrayList<File> attachments = new ArrayList<File>();
        StringBuilder contentBuilder = new StringBuilder();
        Multipart multipart = (Multipart) message.getContent();
        for (int i = 0; i < multipart.getCount(); i++) {
            Part part = multipart.getBodyPart(i);
            if (isDispositionAttachmentOrInline(part.getDisposition())) {
                attachments.add(saveToNewFile(part.getFileName(), part.getInputStream()));
            } else {
                contentBuilder.append(part.getContent().toString());
            }
        }
        return createMessageBeanFromMessage(message, StringUtils.chomp(contentBuilder.toString()), attachments);
    }

    private static boolean isDispositionAttachmentOrInline(String disposition) {
        return (StringUtils.isNotEmpty(disposition))
                && ((Part.ATTACHMENT.contentEquals(disposition) || Part.INLINE
                .contentEquals(disposition)));
    }

    private static File saveToNewFile(String filename, InputStream inputStream) {
        File file = null;
        try {
            file = File.createTempFile(filename,null);
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return file;
    }

    private static List<MessageBean> filterMessages(String sender, String subject, String body, Folder folder) throws MessagingException, IOException {
        Message[] messages = folder.getMessages();
        List<MessageBean> filteredMessages = new ArrayList<MessageBean>(messages.length);
        for (Message message : messages) {
            MessageBean beenMessage = getPart((MimeMessage) message);
            LOGGER.debug("Email was received from: " + beenMessage.getFrom() + " with subject: " + beenMessage.getSubject());
            if (checkSenderAndSubjectMatches(sender, subject, beenMessage) && checkBodyMatches(body, beenMessage)) {
                filteredMessages.add(beenMessage);
            }
        }
        return filteredMessages;
    }

    private static boolean checkBodyMatches(String body, MessageBean beenMessage) {
        return transformNewLineCharsToLinuxStyle(beenMessage.getContent()).equals(transformNewLineCharsToLinuxStyle(body));
    }

    private static boolean checkSenderAndSubjectMatches(String sender, String subject, MessageBean beenMessage) {
        return beenMessage.getFrom().equals(sender) && beenMessage.getSubject().equals(subject);
    }

    private static String transformNewLineCharsToLinuxStyle(String content) {
        return content.replaceAll(IOUtils.LINE_SEPARATOR_WINDOWS, IOUtils.LINE_SEPARATOR_UNIX);
    }
}