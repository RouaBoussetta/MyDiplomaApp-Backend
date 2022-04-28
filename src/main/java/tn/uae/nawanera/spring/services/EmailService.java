package tn.uae.nawanera.spring.services;

import org.springframework.mail.SimpleMailMessage;
public interface EmailService {
	public void sendEmail(SimpleMailMessage email);
}