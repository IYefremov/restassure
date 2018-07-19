package com.cyberiansoft.test.email.emaildata;

public enum EmailHost {

    GMAIL("Gmail"),
    OUTLOOK("Outlook");

    private String emailHost;

    private EmailHost(String emailHost){
        this.emailHost = emailHost;
    }

    public String getEmailHost() {
        return emailHost;
    }
}
