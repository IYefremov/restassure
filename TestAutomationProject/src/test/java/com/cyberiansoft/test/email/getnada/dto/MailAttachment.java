package com.cyberiansoft.test.email.getnada.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MailAttachment {

    @JsonProperty("uid")
    private String attachmentuid;

    @JsonProperty("name")
    private String filename;

    public String getAttachmentId() {
        return attachmentuid;
    }

    public String getAttachmentFileName() {
        return filename;
    }
}
