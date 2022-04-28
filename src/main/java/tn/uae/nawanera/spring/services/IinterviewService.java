package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Interview;
 
public interface IinterviewService {
 
 	List<Interview> retreiveInterviews();
	Interview planifyInterview(Interview interview, int idapp);

}
