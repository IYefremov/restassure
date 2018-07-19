package com.cyberiansoft.test.email.mailpropertieproveders;

import java.util.Properties;

public class GmailPropertiesProvider {

    public static Properties getGmailProperties() {
        Properties props = new Properties();
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", 993);
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", String.valueOf(993));
        return props;
    }
}
