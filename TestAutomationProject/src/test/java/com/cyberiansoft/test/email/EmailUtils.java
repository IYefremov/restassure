package com.cyberiansoft.test.email;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.email.mailpropertieproveders.GmailPropertiesProvider;
import com.cyberiansoft.test.email.mailpropertieproveders.OutlookPropertiesProvider;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private Folder folder;
    private static final String OUTLOOK_SERVER = "imap-mail.outlook.com";
    private static final int DEFAULT_WAIT_MINUTES = 3;
    private static final int WAIT_BETWEEEN_ITERATIONS_SEC = 30;
    private static final int DEFAULT_MESSAGES_TO_SEARCH = 10;

    public EmailUtils(EmailHost emailHost, String userName, String userPassword, EmailFolder emailFolder) throws MessagingException {
        Properties props;
        Session session;
        Store store = null;
        switch (emailHost) {
            case GMAIL:
                props = GmailPropertiesProvider.getGmailProperties();
                session = Session.getDefaultInstance(props);
                store = session.getStore("imap");
                store.connect(userName, userPassword);
                System.out.println("Connected to Gmail server.");
                break;
            case OUTLOOK:
                props = OutlookPropertiesProvider.getOutlookProperties();
                session = Session.getInstance(props, null);
                store = session.getStore();
                store.connect(OUTLOOK_SERVER, userName, userPassword);
                System.out.println("Connected to Outlook server.");
                break;
        }
        folder = store.getFolder(emailFolder.getFolderName());
        folder.open(Folder.READ_WRITE);
    }


    public static class MailSearchParametersBuilder {
        private String subjectKeyword;
        private String attachmentFileName;
        private boolean unreadOnly = false;
        private int maxToSearch = DEFAULT_MESSAGES_TO_SEARCH;
        private Duration waitTime = Duration.ofMinutes(DEFAULT_WAIT_MINUTES);
        private Duration waitInterval = Duration.ofSeconds(WAIT_BETWEEEN_ITERATIONS_SEC);

        public MailSearchParametersBuilder withSubject(String subjectKeyword) {
            this.subjectKeyword = subjectKeyword;
            return this;
        }

        public String getSubjectKeyword() {
            return this.subjectKeyword;
        }

        public MailSearchParametersBuilder withSubjectAndAttachmentFileName(String subjectKeyword, String attachmentFileName) {
            this.subjectKeyword = subjectKeyword;
            this.attachmentFileName = attachmentFileName;
            return this;
        }

        public String getAttachmentFileName() {
            return this.attachmentFileName;
        }

        public MailSearchParametersBuilder unreadOnlyMessages(boolean unreadOnly) {
            this.unreadOnly = unreadOnly;
            return this;
        }

        public boolean isUnreadOnlyMessages() {
            return this.unreadOnly;
        }

        public MailSearchParametersBuilder maxMessagesToSearch(int maxToSearch) {
            this.maxToSearch = maxToSearch;
            return this;
        }

        public int getMaxMessagesToSearch() {
            return this.maxToSearch;
        }

        public MailSearchParametersBuilder waitTimeForMessage(Duration waitTime, Duration waitInterval) {
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

    public int getNumberOfMessages() throws MessagingException {
        return folder.getMessageCount();
    }

    public int getNumberOfUnreadMessages()throws MessagingException {
        return folder.getUnreadMessageCount();
    }

    private Message getMessageByIndex(int index) throws MessagingException {
        return folder.getMessage(index);
    }

    private Message getLatestMessage() throws MessagingException{
        return getMessageByIndex(getNumberOfMessages());
    }


    private Message[] getAllMessages() throws MessagingException {
        return folder.getMessages();
    }

    private Message[] getAllUnreadMessages() throws MessagingException {
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        Flags recent = new Flags(Flags.Flag.RECENT);
        FlagTerm recentFlagTerm = new FlagTerm(recent, false);
        SearchTerm searchTerm = new AndTerm(unseenFlagTerm, recentFlagTerm);
        Message[] foundMessages = folder.search(searchTerm);
        return foundMessages;
    }

    private Message[] getMessages(int maxToGet) throws MessagingException {
        Map<String, Integer> indices = getStartAndEndIndices(maxToGet);
        return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
    }

    private Message[] getMessages(MailSearchParametersBuilder mailSearchParameters) throws Exception {
        Map<String, Integer> indices = getStartAndEndIndices(mailSearchParameters.getMaxMessagesToSearch());
        Message messages[] = getMessages(mailSearchParameters.getMaxMessagesToSearch());
        if(mailSearchParameters.isUnreadOnlyMessages()){
            messages = getUnreadMessages(messages);
        }
        if (mailSearchParameters.getSubjectKeyword() != null)
            messages = getMessagesWithSubject(messages, mailSearchParameters.getSubjectKeyword());
        if (mailSearchParameters.getAttachmentFileName() != null)
            messages = getMessagesWithAttachmentFileName(messages, mailSearchParameters.getAttachmentFileName());
        return messages;
    }

    private Message[] getMessagesAndDownloadAttachments(MailSearchParametersBuilder mailSearchParameters) throws Exception {
        Map<String, Integer> indices = getStartAndEndIndices(mailSearchParameters.getMaxMessagesToSearch());
        Message messages[] = getMessages(mailSearchParameters.getMaxMessagesToSearch());
        if(mailSearchParameters.isUnreadOnlyMessages()){
            messages = getUnreadMessages(messages);
        }
        if (mailSearchParameters.getSubjectKeyword() != null)
            messages = getMessagesWithSubject(messages, mailSearchParameters.getSubjectKeyword());
        if (mailSearchParameters.getAttachmentFileName() != null)
            messages = getMessagesAndDownloadhAttachmentFileName(messages, mailSearchParameters.getAttachmentFileName());
        return messages;
    }

    private Message[] getMessagesAndDownloadhAttachmentFileName(Message messages[], String attachmentFileName) {
        List<Message> matchMessages = new ArrayList<>();
        for (Message message : messages) {
            if (isMessageAttachmentExists(message, attachmentFileName)) {
                downloadMessageAttachment(message);
                matchMessages.add(message);
            }
        }
        return matchMessages.toArray(new Message[]{});
    }

    private Message[] getMessagesWithAttachmentFileName(Message messages[], String attachmentFileName) {
        List<Message> matchMessages = new ArrayList<>();
        for (Message message : messages) {
            if (isMessageAttachmentExists(message, attachmentFileName))
                matchMessages.add(message);
        }
        return matchMessages.toArray(new Message[]{});
    }


    private Message[] getUnreadMessages(Message messages[]) throws MessagingException {
        List<Message> unreadMessages = new ArrayList<>();
        for (Message message : messages) {
            if(isMessageUnread(message)) {
                unreadMessages.add(message);
            }
        }
        return unreadMessages.toArray(new Message[]{});
    }

    private Message[] getMessagesWithSubject(Message messages[], String subjectKeyword) throws MessagingException {
        List<Message> matchMessages = new ArrayList<>();
        for (Message msg : messages) {
            if (msg.getSubject().contains(subjectKeyword)) {
                matchMessages.add(msg);

            }
        }
        return matchMessages.toArray(new Message[]{});
    }


    public List<String> getUrlsFromMessage(Message message, String linkText) throws Exception{
        String html = getMessageContent(message);
        List<String> allMatches = new ArrayList<>();
        Matcher matcher = Pattern.compile("(<a [^>]+>)"+linkText+"</a>").matcher(html);
        while (matcher.find()) {
            String aTag = matcher.group(1);
            allMatches.add(aTag.substring(aTag.indexOf("http"), aTag.indexOf("\">")));
        }
        return allMatches;
    }

    private Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
        int endIndex = getNumberOfMessages();
        int startIndex = endIndex - max;

        //In event that maxToGet is greater than number of messages that exist
        if(startIndex < 1){
            startIndex = 1;
        }

        Map<String, Integer> indices = new HashMap<>();
        indices.put("startIndex", startIndex);
        indices.put("endIndex", endIndex);

        return indices;
    }

    public boolean isTextInMessage(Message message, String text) throws Exception {
        String content = getMessageContent(message);

        //Some Strings within the email have whitespace and some have break coding. Need to be the same.
        content = content.replace("&nbsp;", " ");
        return content.contains(text);
    }

    public boolean isMessageInFolder(MailSearchParametersBuilder mailSearchParameters) {
        int messagesFound = 0;
        try {
            messagesFound = getMessages(mailSearchParameters).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messagesFound > 0;
    }

    public boolean isMessageUnread(Message message) throws MessagingException {
        return !message.isSet(Flags.Flag.SEEN);
    }

    public String waitForMessageWithSubjectInFolderAndGetMailMessage(MailSearchParametersBuilder mailSearchParameters) throws Exception {
        boolean found = false;
        boolean timeout = false;
        String mailMessage = null;
        long waitedTime = 0;
        while (!found && !timeout) {
            found = isMessageInFolder(mailSearchParameters);
            if (!found)
                if (waitedTime < mailSearchParameters.getWaitTimeForMessage().toMillis()) {
                    BaseUtils.waitABit(mailSearchParameters.getWaitIntervalTime().toMillis());
                    waitedTime = waitedTime + mailSearchParameters.getWaitIntervalTime().toMillis();
                } else
                    timeout = true;
            else
                mailMessage = getMessageContent(getMessages(mailSearchParameters)[0]);
        }
        return mailMessage;
    }

    public boolean waitForMessageWithSubjectInFolder(MailSearchParametersBuilder mailSearchParameters) {
        boolean found = false;
        boolean timeout = false;
        long waitedTime = 0;
        while (!found && !timeout) {
            found = isMessageInFolder(mailSearchParameters);
            if (!found)
                if (waitedTime < mailSearchParameters.getWaitTimeForMessage().toMillis()) {
                    BaseUtils.waitABit(mailSearchParameters.getWaitIntervalTime().toMillis());
                    waitedTime = waitedTime + mailSearchParameters.getWaitIntervalTime().toMillis();
                } else
                    timeout = true;
        }
        return found;
    }

    public boolean waitForMessageWithSubjectAndDownloadAttachment(MailSearchParametersBuilder mailSearchParameters) throws Exception  {
        boolean found = false;
        boolean timeout = false;
        long waitedTime = 0;
        while (!found && !timeout) {
            found = getMessagesAndDownloadAttachments(mailSearchParameters).length > 0;
            if (!found)
                if (waitedTime < mailSearchParameters.getWaitTimeForMessage().toMillis()) {
                    BaseUtils.waitABit(mailSearchParameters.getWaitIntervalTime().toMillis());
                    waitedTime = waitedTime + mailSearchParameters.getWaitIntervalTime().toMillis();
                } else
                    timeout = true;
        }
        return found;
    }



    private String getMessageContent(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getMessageContent(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getMessageContent(bp);
                    if (s != null)
                        return s;
                } else {
                    return getMessageContent(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getMessageContent(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }

    public boolean downloadMessageAttachment(Message message) {
        boolean downloaded = false;
        try {
            List<File> attachments = new ArrayList<>();
            Multipart multipart = (Multipart) message.getContent();

            for (int j = 0; j < multipart.getCount(); j++) {
                BodyPart bodyPart = multipart.getBodyPart(j);
                if (bodyPart.getContentType().toLowerCase().contains("mixed")) {
                    Multipart multipart1 = (Multipart) bodyPart.getContent();
                    for (int k = 0; k < multipart1.getCount(); k++) {
                        BodyPart bodyPart1 = multipart1.getBodyPart(k);
                        if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart1.getDisposition()) &&
                                !StringUtils.isNotBlank(bodyPart1.getFileName())) {
                            continue; // dealing with attachments only
                        }
                        InputStream is = bodyPart1.getInputStream();
                        File f = new File(bodyPart1.getFileName());
                        FileOutputStream fos = new FileOutputStream(f);
                        byte[] buf = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buf)) != -1) {
                            fos.write(buf, 0, bytesRead);
                        }
                        fos.close();
                        attachments.add(f);
                        downloaded = true;
                        break;
                    }
                } else if ((bodyPart.getDisposition() != null) &&
                        (bodyPart.getDisposition().equals(Part.ATTACHMENT) || bodyPart.getDisposition().equals(Part.INLINE) )
                        ){
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)bodyPart;
                    if (!mbp.isMimeType("text/plain")) {
                        InputStream is = bodyPart.getInputStream();
                        File f = new File(bodyPart.getFileName());
                        FileOutputStream fos = new FileOutputStream(f);
                        byte[] buf = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buf)) != -1) {
                            fos.write(buf, 0, bytesRead);
                        }
                        fos.close();
                        attachments.add(f);
                        downloaded = true;
                        break;
                    }
                }
            }
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException.");
            ex.printStackTrace();
        }
        return downloaded;
    }

    public boolean isMessageAttachmentExists(Message message, String attachmentFileName) {
        boolean exists = false;
        try {
            Multipart multipart = (Multipart) message.getContent();
            for (int j = 0; j < multipart.getCount(); j++) {
                BodyPart bodyPart = multipart.getBodyPart(j);
                if (bodyPart.getContentType().toLowerCase().contains("mixed")) {
                    Multipart multipart1 = (Multipart) bodyPart.getContent();
                    for (int k = 0; k < multipart1.getCount(); k++) {
                        BodyPart bodyPart1 = multipart1.getBodyPart(k);
                        if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart1.getDisposition()) &&
                                !StringUtils.isNotBlank(bodyPart1.getFileName())) {
                            continue; // dealing with attachments only
                        }
                        if (bodyPart1.getFileName().equals(attachmentFileName)) {
                            exists = true;
                            break;
                        }
                    }
                } else if ((bodyPart.getDisposition() != null) &&
                        (bodyPart.getDisposition().equals(Part.ATTACHMENT) || bodyPart.getDisposition().equals(Part.INLINE) )
                        ){
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)bodyPart;
                    if (!mbp.isMimeType("text/plain")) {
                        if (bodyPart.getFileName().equals(attachmentFileName)) {
                            exists = true;
                            break;
                        }
                    }
                }
            }
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException.");
            ex.printStackTrace();
        }
        return exists;
    }
}

