package io.tapack.satisfy.email.utils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //Required params for sending messages
    private String from;
    private String to;
    private String subject;
    private String content;
    // Optional params for filtering purposes
    private String dateSent;
    private boolean isNew;
    private int msgId;
    private List<File> attachments;

    public MessageBean(Builder builder) {
        subject = builder.subject;
        from = builder.from;
        to = builder.to;
        dateSent = builder.dateSent;
        content = builder.content;
        isNew = builder.isNew;
        msgId = builder.msgId;
        attachments = builder.attachments;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from;
    }

    public String getDateSent() {
        return dateSent;
    }

    public String getContent() {
        return content;
    }

    public String getTo() {
        return to;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getMsgId() {
        return msgId;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public static class Builder {
        private String subject;
        private String from;
        private String to;
        private String dateSent;
        private String content;
        private boolean isNew;
        private int msgId;
        private List<File> attachments;

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder dateSent(String dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder setNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public Builder msgId(int msgId) {
            this.msgId = msgId;
            return this;
        }

        public Builder attachments(List<File> attachments) {
            this.attachments = attachments;
            return this;
        }

        public MessageBean build() {
            return new MessageBean(this);
        }

    }
}
