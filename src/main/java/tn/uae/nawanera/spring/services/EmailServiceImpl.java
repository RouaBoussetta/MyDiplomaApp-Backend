package tn.uae.nawanera.spring.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
	 
	 
	  void sendEmail() {

	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo("boussettaroua@gmail.com");

	        msg.setSubject("Testing from Spring Boot");
	        msg.setText("Hello World \n Spring Boot Email");

	        mailSender.send(msg);

	    }
	  @Async
	public
	    void sendEmailWithAttachment() throws MessagingException, IOException {

	        MimeMessage msg = mailSender.createMimeMessage();

	        // true = multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	        helper.setTo("boussettaroua@gmail.com");

	        helper.setSubject("Testing from Spring Boot");

	        // default = text/plain
	        //helper.setText("Check attachment for image!");

	        // true = text/html
	        helper.setText(
	        	 
	        		"<h1>Check attachment for image!</h1>", true);
	 

	        helper.addAttachment("myDiplomaLogo.png", new ClassPathResource("myDiplomaLogo.png"));

	        mailSender.send(msg);

	    }

  

}
