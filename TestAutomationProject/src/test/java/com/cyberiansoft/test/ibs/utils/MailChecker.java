package com.cyberiansoft.test.ibs.utils;

import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cyberiansoft.test.inhouse.pageObject.BasePage.waitABit;

public class MailChecker {

    private static String userName;
    private static String userPassword;
    private static String senderMail = "noreply@repair360.net";

    public MailChecker() {
        userName = InHouseConfigInfo.getInstance().getUserEmail();
        userPassword = InHouseConfigInfo.getInstance().getUserPassword();
    }

    public static Store loginToGMailBox(String userName, String password) {
		Properties properties = new Properties();
		Store store = null;
        // server setting
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", 993);
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",String.valueOf(993));
        Session session = Session.getDefaultInstance(properties);
       
        System.out.println("Connected to Email server.");
        try {
            // connects to the message store
            store = session.getStore("imap");
            store.connect(userName, password);
            System.out.println("Connected to Email server.");
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
		
		return store;
	}
	
	
	public static Folder getInboxMailMessagesFromSpam(Store store) {
		Folder folderAll = null;
		try {
			Folder[] f = store.getDefaultFolder().list();
			for(Folder fd:f){
			    Folder t[]=fd.list();
			    for(Folder f1:t) {
			    	System.out.println("==========" + f1.getName());
			    }
				for(Folder f1:t)
					if  (f1.getName().equals("Спам")) {
						try {
							folderAll = f1;
							folderAll.open(Folder.READ_WRITE);
						} catch (MessagingException ex) {
							System.out.println("No provider.");
							ex.printStackTrace();
						}
						break;
					}
			    }
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return folderAll;
	}
	
	public static Folder getSpamMailMessages(Store store) {
		Folder folderSpam = null;
		try {
			Folder[] f = store.getDefaultFolder().list();
			for(Folder fd:f){
			    Folder t[]=fd.list();
			    for(Folder f1:t) {
			    	System.out.println("==========" + f1.getName());
			    }
			    for(Folder f1:t)
			    	if  (f1.getName().equals("Spam")) {
			    		try {
			    			folderSpam = f1;
			    			folderSpam.open(Folder.READ_WRITE);					
			    		} catch (MessagingException ex) {
			                System.out.println("No provider.");
			                ex.printStackTrace();
			    		}
			    		break;
			    	}
			        
			    }
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return folderSpam;
	}
	
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param keyword
     * @throws IOException
     */
	@SuppressWarnings("unused")
	private static boolean textIsHtml = false;
    /**
     * Return the primary text content of the message.
     */
    public static String getText(Part p) throws MessagingException,IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }
    
    public static boolean findMailWithMessageText(Message[] foundMessages, final String subjectKeyword, final String fromEmail, final String bodySearchText) {
    	boolean val = false;
    	try {
    		for (int i=foundMessages.length-1 ; i>=foundMessages.length-3; i--) {
    			Message message = foundMessages[i];
    			Address[] froms = message.getFrom();
    			String email = froms == null ? null : ((InternetAddress)froms[0]).getAddress();
                System.out.println("EMAIL: " + email);
    			if(message.getSubject()==null){
    				continue;
    			}
    			Date date = new Date();//Getting Present date from the system
    			long diff = date.getTime()-message.getReceivedDate().getTime();//Get The difference between two dates
    			long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute

    			System.out.println("Difference in Minutes b/w present time & Email Received time :" +diffMinutes);
            
             	System.out.println("Current "+ i + " :"+ "Subject:"+ message.getSubject());
             	System.out.println("Current "+ i + " :"+ "Subject:"+ email);
             	System.out.println("Current "+ i + " :"+ "Subject:"+ email);
             	
             	if (message.getSubject().contains(subjectKeyword) && email.equals(fromEmail) && getText(message).contains(bodySearchText) && diffMinutes<=10) {
             		String subject = message.getSubject();
             		// System.out.println(getText(message));
             		System.out.println("Found message #" + i + ": ");
             		System.out.println("At "+ i + " :"+ "Subject:"+ subject);
             		System.out.println("From: "+ email +" on : "+message.getReceivedDate());
             		if (getText(message).contains(bodySearchText)) {
             			System.out.println("Message contains the search text "+bodySearchText);
             			val=true;
             		}
             		else{
             			val=false;
             		}
             		break;
             	}
           
    		}
    	} catch (IOException ex) {
            System.out.println("IOException.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
       return val;
    }
    
    public static Message findMessage(Message[] foundMessages, final String subjectKeyword, final String fromEmail) {
    	Message requiredmessage = null;
    	
    	try {
    		for (int i=0; i<foundMessages.length; i++) {
    			Message message = foundMessages[i];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress)froms[0]).getAddress();
                if(message.getSubject()==null){
                	continue;
                }
                Date date = new Date();//Getting Present date from the system
                long diff = date.getTime()-message.getReceivedDate().getTime();//Get The difference between two dates
                long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute
         
                System.out.println("Difference in Minutes b/w present time & Email Received time :" +diffMinutes);
                System.out.println("Current "+ i + " :"+ "Subject:"+ message.getSubject());
                System.out.println("Current "+ i + " :"+ "Subject:"+ email); 
            	System.out.println(message.getSubject());
                if (message.getSubject().contains(subjectKeyword) && email.equals(fromEmail) && diffMinutes<=10) {
                	requiredmessage = message;
                }
    		}
    		
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
    	return requiredmessage;
    }
    
    public static boolean searchEmail(String userName, String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) {
    	boolean val = false;	
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessagesFromSpam(store);
            //create a search term for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			//create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm,recentFlagTerm);
            Message[] foundMessages = folderInbox.search(searchTerm);
            System.out.println("Total Messages Found :"+ foundMessages.length);
            val = findMailWithMessageText(foundMessages, subjectKeyword, fromEmail, bodySearchText);
            // disconnect
            folderInbox.close(false);
            store.close();
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        return val;
   }
    
    public static boolean searchSpamEmail(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) {
    	boolean val = false;	
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderSpam = getSpamMailMessages(store);
            //create a search term for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			//create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm,recentFlagTerm); 
            System.out.println("Is open: " + folderSpam.isOpen());
            Message[] foundMessages = folderSpam.search(searchTerm);
            System.out.println("Total Messages Found :"+ foundMessages.length);
            val = findMailWithMessageText(foundMessages, subjectKeyword, fromEmail, bodySearchText);
            // disconnect
            folderSpam.close(false);
            store.close();
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        return val;
   }
    

    public static boolean downloadMessageAttachment(Message message, String attachmentfilename) {
    	boolean downloaded = false;
    	try {
    		List<File> attachments = new ArrayList<>();
    		Multipart multipart = (Multipart) message.getContent();
    		for (int j = 0; j < multipart.getCount(); j++) {
    			BodyPart bodyPart = multipart.getBodyPart(j);
    			if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
    				!StringUtils.isNotBlank(bodyPart.getFileName())) {
    					continue; // dealing with attachments only
    				} 
    			InputStream is = bodyPart.getInputStream();
    			File f = new File(bodyPart.getFileName());
    			FileOutputStream fos = new FileOutputStream(f);
    			byte[] buf = new byte[4096];
    			int bytesRead;
    			while((bytesRead = is.read(buf))!=-1) {
    				fos.write(buf, 0, bytesRead);
    			}
    			fos.close();
    			attachments.add(f);
    			downloaded = true;
    			break;
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
    
    
    public static boolean searchEmailAndGetAttachment(String userName, String password, final String subjectKeyword, final String fromEmail, String attachmentfilename) {
    	
    	boolean val = false;
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessagesFromSpam(store);
            //create a search term for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			//create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm,recentFlagTerm);
            Message[] foundMessages = folderInbox.search(searchTerm);
            System.out.println("Total Messages Found :"+ foundMessages.length);
            //val = findMailWithMessageText(foundMessages, subjectKeyword, fromEmail, bodySearchText);
            Message message = findMessage(foundMessages, subjectKeyword, fromEmail);
            if (message != null)        
            	val = downloadMessageAttachment(message, attachmentfilename);
            
            // disconnect
            folderInbox.close(false);
            store.close();
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        return val; 	
    }
    
    public static String searchEmailAndGetMailMessageFromSpam(String userName, String password, final String subjectKeyword, final String fromEmail) {
    	String mailmessage = "";
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessagesFromSpam(store);
            //create a search term for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			//create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm,recentFlagTerm);
            Message[] foundMessages = folderInbox.search(searchTerm);
            System.out.println("Total Messages Found :"+ foundMessages.length);
            Message message = findMessage(foundMessages, subjectKeyword, fromEmail);
            if (message != null)        
            	mailmessage = getText(message);
            
            //message.setFlag(Flags.Flag.SEEN, true);
            message.setFlag(Flags.Flag.DELETED, true);
            // disconnect
            folderInbox.close(false);
            store.close();
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException.");
            e.printStackTrace();
		}	
    	return mailmessage;
    }
    
    public static String searchSpamEmailAndGetMailMessage(String userName, String password, final String subjectKeyword, final String fromEmail) {
        String mailmessage = "";
        try {
            Store store = loginToGMailBox(userName, password);

            Folder folderInbox = getSpamMailMessages(store);
            //create a search term for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
            //create a search term for all recent messages
            Flags recent = new Flags(Flags.Flag.RECENT);
            FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
            Message[] foundMessages = folderInbox.search(searchTerm);
            System.out.println("Total Messages Found :" + foundMessages.length);
            Message message = findMessage(foundMessages, subjectKeyword, fromEmail);
            if (message != null)
                mailmessage = getText(message);

            //message.setFlag(Flags.Flag.SEEN, true);
            message.setFlag(Flags.Flag.DELETED, true);
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException.");
            e.printStackTrace();
        }
        return mailmessage;
    }

    public ArrayList<String> getLinks(String mailContent) {
//        String mailContent = MailChecker.getUserMailContentFromSpam();
        Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?</a>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(mailContent);
        ArrayList<String> links = new ArrayList<>();
        while (pageMatcher.find()) {
            links.add(pageMatcher.group());
        }
        return links;
    }

    public ArrayList<String> getListOfAgreementLinks(String mailContent) {
        Pattern linkPattern = Pattern.compile("(https://goo.gl/.+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(mailContent);
        ArrayList<String> links = new ArrayList<>();
        while (pageMatcher.find()) {
            String replace = pageMatcher.group().replaceAll("(<.+)", "");
            links.add(replace);
        }
        return links;
    }

    public String getAgreementLink(String userMailContentFromSpam, String partialLinkTextToAgreementPage) {
        return getListOfAgreementLinks(userMailContentFromSpam)
                .stream()
                .filter(l -> l.contains(partialLinkTextToAgreementPage))
                .findFirst()
                .get();
    }

    public static String getMailMessageFromSpam(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) {
    	String mailmessage = "";
		for (int i=0; i < 3; i++) {
			if (!MailChecker.searchEmail(userName, password, subjectKeyword, fromEmail, bodySearchText)) {
				try {
					Thread.sleep(60*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				mailmessage = searchEmailAndGetMailMessageFromSpam(userName, password, subjectKeyword, fromEmail);
				if (mailmessage.length() > 3) {
					break;
				}				
			}
		}
    	return mailmessage; 
    }
    
    public static String getSpamMailMessage(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) {
    	String mailmessage = "";
		if (MailChecker.searchSpamEmail(userName, password, subjectKeyword, fromEmail, bodySearchText)) {
			mailmessage = MailChecker.searchSpamEmailAndGetMailMessage(userName, password, subjectKeyword, fromEmail);
		}
    	return mailmessage;
    }

    public String getSpamMailMessage(String title, String bodySearchText) {
        return getSpamMailMessage(userName, userPassword, title, senderMail, bodySearchText);
    }
    
    public static String getUserRegistrationURLFromSpam() {
    	
		String mailmessage = getUserMailContentFromSpam();
		String confirmationurl = "";
		confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
		return confirmationurl;
    }

    public String getMailContentFromSpam() {
        return MailChecker.getUserMailContentFromSpam();
    }

    public static String getUserMailContentFromSpam() {

		String mailmessage = "";
		for (int i=0; i < 8; i++) {
            try {
                mailmessage = searchEmailAndGetMailMessageFromSpam(userName, userPassword, "Agreement", senderMail);
                if (mailmessage.isEmpty()) {
                    waitABit(60*500);
                } else {
                    break;
                }
            } catch (NullPointerException ignored) {}
		}
		return mailmessage;
    }

    public boolean checkEmails(String title) {
        boolean flag = false;
        waitABit(30000);
        for (int i = 0; i < 5; i++) {
            try {
                if (!searchSpamEmailAndGetMailMessage(userName, userPassword, title, senderMail).isEmpty()) {
                    flag = true;
                    break;
                }
            } catch (NullPointerException ignored) {}
            waitABit(40000);
        }
        return flag;
    }
}
