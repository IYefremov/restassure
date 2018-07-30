package com.cyberiansoft.test.email.getnada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class InboxEmail {

    @JsonProperty("uid")
    private String messageId;

    @JsonProperty("f")
    private String from;

    @JsonProperty("s")
    private String subject;

    @JsonProperty("at")
    private List<MailAttachment> mailattachment;

    public String getMessageId() {
        return messageId;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public List<MailAttachment> getMessageAttachments() {
        return mailattachment;
    }
}
