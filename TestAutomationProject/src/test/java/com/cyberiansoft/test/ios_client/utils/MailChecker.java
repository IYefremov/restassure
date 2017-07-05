package com.cyberiansoft.test.ios_client.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang3.StringUtils;

public class MailChecker {
	
	public static Store loginToGMailBox(String userName,String password) {
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
	
	
	public static Folder getInboxMailMessages(Store store) {
		Folder folderInbox = null;
		try {
			folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);					
		} catch (MessagingException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
		}
		
		return folderInbox;
		
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
    			if(message.getSubject()==null){
    				continue;
    			}
    			Date date = new Date();//Getting Present date from the system
    			long diff = date.getTime()-message.getReceivedDate().getTime();//Get The difference between two dates
    			long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute

    			System.out.println("Difference in Minutes b/w present time & Email Recieved time :" +diffMinutes);
            
             	System.out.println("Current "+ i + " :"+ "Subject:"+ message.getSubject());
             	System.out.println("Current "+ i + " :"+ "Subject:"+ email);
             	System.out.println("Current "+ i + " :"+ "Subject:"+ email);
             	
             	if (message.getSubject().contains(subjectKeyword) && email.equals(fromEmail) && getText(message).contains(bodySearchText) && diffMinutes<=10) {
             		String subject = message.getSubject();
             		// System.out.println(getText(message));
             		System.out.println("Found message #" + i + ": ");
             		System.out.println("At "+ i + " :"+ "Subject:"+ subject);
             		System.out.println("From: "+ email +" on : "+message.getReceivedDate());
             		if (getText(message).contains(bodySearchText)== true) {
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
    		for (int i=foundMessages.length-1 ; i>=foundMessages.length-3; i--) {
    			Message message = foundMessages[i];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress)froms[0]).getAddress();
                if(message.getSubject()==null){
                	continue;
                }
                Date date = new Date();//Getting Present date from the system
                long diff = date.getTime()-message.getReceivedDate().getTime();//Get The difference between two dates
                long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute
                // if(diffMinutes>2){
                // diffMinutes=2;
                // }
                System.out.println("Difference in Minutes b/w present time & Email Recieved time :" +diffMinutes);
                System.out.println("Current "+ i + " :"+ "Subject:"+ message.getSubject());
                System.out.println("Current "+ i + " :"+ "Subject:"+ email); 
            	System.out.println(message.getSubject());
                if (message.getSubject().contains(subjectKeyword) && email.equals(fromEmail) && diffMinutes<=5) {
                	requiredmessage = message;
                }
//                if (message.getSubject().contains(subjectKeyword)){
//                	requiredmessage = message;
//                }
//                if (email.equals(fromEmail)){
//                	requiredmessage = message;
//                }
    		}
    		
    	} catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
    	return requiredmessage;
    }
    
    public static boolean searchEmail(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) throws IOException {
    	boolean val = false;	
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessages(store);
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
    

    public static boolean downloadMessageAttachment(Message message, String attachmentfilename) {
    	boolean downloaded = false;
    	try {
    		List<File> attachments = new ArrayList<File>();
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
    
    
    public static boolean searchEmailAndGetAttachment(String userName, String password, final String subjectKeyword, final String fromEmail, String attachmentfilename) throws IOException {
    	
    	boolean val = false;	
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessages(store);
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
    
    public static String searchEmailAndGetMailMessage(String userName, String password, final String subjectKeyword, final String fromEmail) {
    	String mailmessage = "";
    	try {
    		Store store = loginToGMailBox(userName, password);
            
            Folder folderInbox = getInboxMailMessages(store);
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
    
    public static String getMailMessage(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) throws IOException {
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
				mailmessage = MailChecker.searchEmailAndGetMailMessage(userName, password, subjectKeyword, fromEmail);
				if (mailmessage.length() > 3) {
					break;
				}				
			}
		}
    	return mailmessage; 
    }
    
    public static String getUserRegistrationURL() throws IOException {
    	
		String mailmessage = getUserMailContent();
		String confirmationurl = "";
		System.out.println("==========0" + mailmessage);
		System.out.println("==========1" + mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'")));
		confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
		return confirmationurl;
    }
    
    public static String getUserMailContent() throws IOException {
    	
    	final String usermail = "test.cyberiansoft@gmail.com";
    	final String usermailpsw = "ZZzz11!!";
    	final String usermailtitle = "ReconPro vNext Dev: REGISTRATION";
    	final String sendermail = "ReconPro@cyberiansoft.com";
    	final String mailcontainstext = "complete the registration process";
    	
		String mailmessage = "";
		for (int i=0; i < 4; i++) {
			if (!MailChecker.searchEmail(usermail, usermailpsw, usermailtitle, sendermail, mailcontainstext)) {
				waitABit(60*500);
			} else {
				mailmessage = MailChecker.searchEmailAndGetMailMessage(usermail, usermailpsw, usermailtitle, sendermail);	
				break;
			}
		}
		return mailmessage;
    }
    
    
    
    public static void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ex) {
                // Swallow exception
                ex.printStackTrace();
            }
        }
    }
    
}
