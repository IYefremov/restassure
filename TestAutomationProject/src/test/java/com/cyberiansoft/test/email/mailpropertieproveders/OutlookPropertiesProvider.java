package com.cyberiansoft.test.email.mailpropertieproveders;

import java.util.Properties;

public class OutlookPropertiesProvider {

    public static Properties getOutlookProperties() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imap-mail.outlook.com.ssl.enable", "true");
        props.put("mail.pop3.host", "outlook.com");
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.starttls.enable", "true");
        return props;
    }
}
