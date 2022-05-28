package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;

import tn.uae.nawanera.spring.entities.Interview;
 
public interface IinterviewService {
 
 	List<Interview> retreiveInterviews();
	Interview planifyInterview(Interview interview, int idapp) throws IOException, GeneralSecurityException,MessagingException;
	void rejectInterview(int id);
	Interview getInterviewByApplication(int app);

}
