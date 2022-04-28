package tn.uae.nawanera.spring.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Liking;
import tn.uae.nawanera.spring.services.LikingService;

@RestController
@RequestMapping("/api/training-course/liking")
public class LikingController {
	
	@Autowired
	LikingService likingService;

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@PostMapping("/add-like/{idTc}")
	public String addLike( @PathVariable("idTc") int idTc) {
		return  likingService.addLiking( idTc);
	}
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@DeleteMapping("/delete-like/{likeid}")  
	public String deleteLiking(@PathVariable("likeid") int likeid)  
	{  
		return likingService.deleteLiking(likeid);  
	} 
	
	@PermitAll

	@GetMapping("/get-all-likes")  
	public List<Liking> getAllLikes()   
	{  
		return likingService.getAllLikings();  
	}  
	
	
	@GetMapping("/detail-like/{likingid}")  
	public Liking getLike(@PathVariable("likingid") int likingid)   
	{  
		return likingService.getLikingById(likingid);  
	}
	@PermitAll
	@GetMapping("/count-all-likes")
	public int getlikescount() {
		return likingService.countLikings();
	}
	
	@PermitAll
	@GetMapping("/likes-by-user/{idU}")
	public List<Liking> getLikesByUser(@PathVariable("idU") int idU) {
		return likingService.getLikingsByUserId(idU);
	}
	
	@PermitAll
	@GetMapping("/count-user-likes/{idU}")
	public int getuserlikescount(@PathVariable("idU") int idU) {
		return likingService.countLikingsByUser(idU);
	}
	
	@PermitAll
	@GetMapping("/likes-by-training-course/{idTc}")
	public List<Liking> getLikingsByTc(@PathVariable("idTc") int idTc) {
		return likingService.getLikingsByTCId(idTc);
	}
	
	@PermitAll
	@GetMapping("/count-tc-likes/{idTc}")
	public int getTclikescount(@PathVariable("idTc") int idTc) {
		return likingService.countLikingsByTC(idTc);
	}
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/get-my-likes")  
	public List<Liking> getOwnLikes()   
	{  
		return likingService.getOwnLikes();  
	}

	
	
	
	
	
	
	
}
