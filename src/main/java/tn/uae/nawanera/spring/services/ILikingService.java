package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Liking;



public interface ILikingService {
	public String deleteLiking(int id)  ;
	public List<Liking> getAllLikings();
	public Liking getLikingById(int id);
	public int countLikings();
	public List<Liking> getLikingsByUserId(int id);
	public int countLikingsByUser(int id);
	public List<Liking> getLikingsByTCId(int id);
	public int countLikingsByTC(int id);
	public boolean isLikeExists(int idu, int idTc);
 	public String addLiking(int idTc) ;
	public List<Liking> getOwnLikes() ;
}
