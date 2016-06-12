package com.goldfinance.jinGC.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.goldfinance.jinGC.service.EmailService;

public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	private String systemEmail;

	@Override   
	public void sendMail(String to, String subject, String htmlText, boolean html) throws MessagingException {
		sendMail(new String[] { to }, subject, htmlText, html);
	}

	@Override
	public void sendMail(String[] to, String subject, String htmlText, boolean html) throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
		msgHelper.setFrom(systemEmail);
		msgHelper.setTo(to);
		msgHelper.setSubject(subject);
		msgHelper.setText(htmlText, html);

		javaMailSender.send(msg);
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
	

}
