package com.cyberiansoft.test.email.getnada;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NadaEMailService {

    private static final String NADA_EMAIL_DOMAIN = "@nada.ltd";
    private static final String INBOX_MESSAGE_KEY_NAME = "msgs";
    private static final String EMAIL_ID_ROUTE_PARAM = "email-id";
    private static final String MESSAGE_ID_ROUTE_PARAM = "message-id";
    private static final String ATTACH_ID_ROUTE_PARAM = "attach-id";
    private static final String NADA_EMAIL_INBOX_API = "https://getnada.com/api/v1/inboxes/{email-id}";
    private static final String NADA_EMAIL_MESSAGE_API = "https://getnada.com/api/v1/messages/{message-id}";
    private static final String NADA_EMAIL_MESSAGE_ATTACH_API = "https://getnada.com/api/v1/file/{message-id}/{attach-id}";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int EMAIL_CHARS_LENGTH = 10;
    private static final int DEFAULT_WAIT_MINUTES = 5;
    private static final int WAIT_BETWEEEN_ITERATIONS_SEC = 30;


    private String emailId;

    public static class MailSearchParametersBuilder {
        private String subjectKeyword;
        private String attachmentFileName = null;
        private boolean unreadOnly = false;
        private Duration waitTime = Duration.ofMinutes(DEFAULT_WAIT_MINUTES);
        private Duration waitInterval = Duration.ofSeconds(WAIT_BETWEEEN_ITERATIONS_SEC);

        public NadaEMailService.MailSearchParametersBuilder withSubject(String subjectKeyword) {
            this.subjectKeyword = subjectKeyword;
            return this;
        }

        public String getSubjectKeyword() {
            return this.subjectKeyword;
        }

        public NadaEMailService.MailSearchParametersBuilder withSubjectAndAttachmentFileName(String subjectKeyword, String attachmentFileName) {
            this.subjectKeyword = subjectKeyword;
            this.attachmentFileName = attachmentFileName;
            return this;
        }

        public String getAttachmentFileName() {
            return this.attachmentFileName;
        }

        public NadaEMailService.MailSearchParametersBuilder unreadOnlyMessages(boolean unreadOnly) {
            this.unreadOnly = unreadOnly;
            return this;
        }

        public boolean isUnreadOnlyMessages() {
            return this.unreadOnly;
        }

        public NadaEMailService.MailSearchParametersBuilder waitTimeForMessage(Duration waitTime, Duration waitInterval) {
            this.waitTime = waitTime;
            this.waitInterval = waitInterval;
            return this;
        }

        public Duration getWaitTimeForMessage() {
            return this.waitTime;
        }

        public Duration getWaitIntervalTime() {
            return this.waitInterval;
        }
    }

    public NadaEMailService() {
        generateEmailId();
    }

    public NadaEMailService(String emailId) {
        this.emailId = emailId;
    }

    private void generateEmailId(){
        this.emailId = RandomStringUtils.randomAlphanumeric(EMAIL_CHARS_LENGTH).toLowerCase().concat(NADA_EMAIL_DOMAIN);
    }

    public void setEmailId(String nadaEmailAddress){
        this.emailId = nadaEmailAddress;
    }

    //generates a random email for the first time.
    //call reset for a new random email
    public String getEmailId(){
        if(Objects.isNull(this.emailId)){
            this.generateEmailId();
        }
        return this.emailId;
    }

    //to re-generate a new random email id
    public void reset(){
        this.emailId = null;
    }

    private List<InboxEmail> getInbox() throws UnirestException, IOException {
        String msgs = Unirest.get(NADA_EMAIL_INBOX_API)
                .routeParam(EMAIL_ID_ROUTE_PARAM, this.getEmailId())
                .asJson()
                .getBody()
                .getObject()
                .getJSONArray(INBOX_MESSAGE_KEY_NAME)
                .toString();
        return MAPPER.readValue(msgs, new TypeReference<List<InboxEmail>>() {});
    }

    private EmailMessage getMessageById(final String messageId)
    {
        String msgs = null;
        try {
            msgs = Unirest.get(NADA_EMAIL_MESSAGE_API)
                    .routeParam(MESSAGE_ID_ROUTE_PARAM, messageId)
                    .asJson()
                    .getBody()
                    .getObject()
                    .toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {
            return MAPPER.readValue(msgs, EmailMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String deleteMessageById(final String messageId)
    {
        String msg = null;
        try {
            msg = Unirest.delete(NADA_EMAIL_MESSAGE_API)
                    .routeParam(MESSAGE_ID_ROUTE_PARAM, messageId).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void getAttachmentByMessageIdAndAttachmentId(final String messageId, final String attachid,
                                                         final String attachmentFileName) throws IOException {
        String msgs = null;
        try {
            InputStream is = Unirest.get(NADA_EMAIL_MESSAGE_ATTACH_API)
                    .routeParam(MESSAGE_ID_ROUTE_PARAM, messageId).routeParam(ATTACH_ID_ROUTE_PARAM, attachid).asBinary().getBody();
            File f = new File(attachmentFileName);
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

    private EmailMessage getMessageWithSubject(final String emailSubject) throws IOException, UnirestException {
        return  this.getInbox()
                .stream()
                .filter(ie -> ie.getSubject().contains(emailSubject))
                .findFirst()
                .map(InboxEmail::getMessageId)
                .map(this::getMessageById)
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isMessageWithSubjectPresent(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws IOException, UnirestException {
        return  this.getInbox()
                .stream()
                .filter(ie -> ie.getSubject().contains(mailSearchParameters.getSubjectKeyword()))
                .findFirst()
                .map(InboxEmail::getMessageId).isPresent();
    }

    private List<MailAttachment> getMessageAttachments(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws IOException, UnirestException {
        return  this.getInbox()
                .stream()
                .filter(ie -> ie.getSubject().contains(mailSearchParameters.getSubjectKeyword()))
                .findFirst()
                .map(InboxEmail::getMessageAttachments)
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isMessageWithSubjectAndAttachmentPresent(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws IOException, UnirestException {
        boolean present = false;
        List<MailAttachment> attachments = getMessageAttachments(mailSearchParameters);
        if (attachments.size() > 0)
            present = attachments.stream()
                    .filter(att -> att.getAttachmentFileName().equals(mailSearchParameters.getAttachmentFileName()))
                    .findFirst().isPresent();
        return  present;
    }

    public String getMailMessageBySybjectKeywords(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws Exception {
        EmailMessage message = null;
        if (waitForMessage(mailSearchParameters)) {
            message = getMessageWithSubject(mailSearchParameters.getSubjectKeyword());
        }
        return message.getHtml();
    }

    public boolean downloadMessageAttachment(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws Exception {
        if (waitForMessage(mailSearchParameters)) {
            InboxEmail inboxEmail = this.getInbox()
                    .stream()
                    .filter(ie -> ie.getSubject().contains(mailSearchParameters.getSubjectKeyword()))
                    .findFirst().get();
            MailAttachment attachment = getMessageAttachments(mailSearchParameters)
                    .stream()
                    .filter(att -> att.getAttachmentFileName().equals(mailSearchParameters.getAttachmentFileName()))
                    .findFirst().get();
            getAttachmentByMessageIdAndAttachmentId(inboxEmail.getMessageId(), attachment.getAttachmentId(),
                    mailSearchParameters.getAttachmentFileName());
            return true;
        }
        return  false;
    }

    public boolean waitForMessage(NadaEMailService.MailSearchParametersBuilder mailSearchParameters) throws Exception  {
        boolean found = false;
        boolean timeout = false;
        long waitedTime = 0;
        while (!found && !timeout) {
            found = isMessageWithSubjectPresent(mailSearchParameters);
            if (!found)
                if (waitedTime < mailSearchParameters.getWaitTimeForMessage().toMillis()) {
                    BaseUtils.waitABit(mailSearchParameters.getWaitIntervalTime().toMillis());
                    waitedTime = waitedTime + mailSearchParameters.getWaitIntervalTime().toMillis();
                } else
                    timeout = true;
        }
        if (!timeout)
            if (mailSearchParameters.attachmentFileName != null)
                found = isMessageWithSubjectAndAttachmentPresent(mailSearchParameters);
        return found;
    }

    public void deleteMessageWithSubject(final String emailSubject) throws IOException, UnirestException {
          this.getInbox()
                .stream()
                .filter(ie -> ie.getSubject().contains(emailSubject))
                .findFirst()
                .map(InboxEmail::getMessageId)
                .map(this::deleteMessageById)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<String> getUrlsFromMessage(String message, String linkText) {
        return getUrlsFromMessage(message, linkText, "http", "\">");
    }

    public List<String> getUrlsFromMessage(String message, String linkText, String start, String end) {
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(<a [^>]+>)" + linkText + "<.a>").matcher(message);
        while (matcher.find()) {
            String aTag = matcher.group(1);
            allMatches.add(aTag.substring(aTag.indexOf(start), aTag.indexOf(end)));
        }
        return allMatches;
    }

    public List<String> getUrlFromMessageWithSubject(MailSearchParametersBuilder builder, String message, String linkText) throws IOException, UnirestException {
        if (isMessageWithSubjectPresent(builder)) {
            return getUrlsFromMessage(message, linkText);
        } else {
            return null;
        }
    }

    public void deleteAllMessages() throws IOException, UnirestException {
       List<InboxEmail> InboxEmails =  getInbox();
       for (InboxEmail mail : InboxEmails) {
           deleteMessageById(mail.getMessageId());
       }

    }
}
