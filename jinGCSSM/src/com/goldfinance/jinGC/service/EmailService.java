package com.goldfinance.jinGC.service;
import javax.mail.MessagingException;

public interface EmailService {
	
    public void sendMail(String to, String subject, String htmlText, boolean html) throws MessagingException;
    
    public void sendMail(String[] to, String subject, String htmlText , boolean html) throws MessagingException;
}
