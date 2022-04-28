package tn.uae.nawanera.spring.services;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
 
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	 
	
	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
		
		
	}
	
	 public void sendMailToIntern(String email) throws MessagingException {
		 javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	        

	        
	        helper.setText(email, true);
 	        mailSender.send(mimeMessage);
	    }

  

}
