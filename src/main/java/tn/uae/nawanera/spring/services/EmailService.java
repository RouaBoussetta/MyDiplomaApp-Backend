package tn.uae.nawanera.spring.services;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;
public interface EmailService {
	public void sendEmail(SimpleMailMessage email);
	 void sendEmailWithAttachment() throws MessagingException, IOException ;
}