package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Liking;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.repositories.ILikingRepository;
import tn.uae.nawanera.spring.repositories.ITrainingCourseRepository;

@Service
public class LikingService implements ILikingService{
	@Autowired 
	ITrainingCourseRepository itcRepository;
	
	@Autowired 
	ILikingRepository iLikingRepository;
	
	@Autowired
	IUserservice iuserService;
	
	
	@Override
	public String deleteLiking(int id)  {
	
		int iduser = iuserService.currentUser().getId();
		Liking l = iLikingRepository.findById(id).get();
		if (iduser==l.getUser().getId()){
		iLikingRepository.deleteById(id);
		return ("Like deleted successfully");
		}
		else{
		return ("You are not allowed to delete this like");	
		}
	}

	@Override
	public List<Liking> getAllLikings() {
		List<Liking>likes = new ArrayList<>();
		iLikingRepository.findAll().forEach(likes::add);
		return likes;
	}

	@Override
	public Liking getLikingById(int id) {
		return iLikingRepository.findById(id).get();  
	}

	@Override
	public int countLikings() {
		List <Liking> likes= iLikingRepository.findAll();
		return likes.size();
	}

	@Override
	public List<Liking> getLikingsByUserId(int id) {
		return iLikingRepository.getLikesByUserId(id);
	}

	@Override
	public int countLikingsByUser(int id) {
		List <Liking> likes= iLikingRepository.getLikesByUserId(id);
		return likes.size();
	}

	@Override
	public List<Liking> getLikingsByTCId(int id) {
		TrainingCourse tc=itcRepository.findById(id).get();
		return iLikingRepository.findByTrainingCourse(tc);
	}

	@Override
	public int countLikingsByTC(int id) {
		TrainingCourse tc=itcRepository.findById(id).get();
		return iLikingRepository.findByTrainingCourse(tc).size();
	}

	@Override
	public boolean isLikeExists(int idu, int idTc) {
		 int count =iLikingRepository.isLikeExists(idu, idTc);
		 if (count==0){
			return false;
		}
		 else {
			 return true;
		 }
	}


	@Override
	public String addLiking(int idTc)   {
		int iduser = iuserService.currentUser().getId();
		Liking l = new Liking();
		if (isLikeExists(iduser, idTc)){
			return ("You already liked this post");
		}
		else{
		TrainingCourse tc=itcRepository.findById(idTc).get();
		l.setUser(iuserService.currentUser());
		l.setTrainingCourse(tc);
		l.setLikeDate(LocalDateTime.now());
		 
			iLikingRepository.save(l);
			return "number of likes on this post: " + countLikingsByTC(idTc);
		
		}
	}

	@Override
	public List<Liking> getOwnLikes()  {
		return iLikingRepository.getLikesByUserId(iuserService.currentUser().getId());
	}

}
