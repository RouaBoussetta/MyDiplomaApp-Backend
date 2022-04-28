package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Comment;
import tn.uae.nawanera.spring.services.CommentService;

@RestController
@RequestMapping("/api/training-course/comments")
public class CommentController {

	@Autowired
	CommentService commentService;

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@PostMapping("/add-comment/{idTc}")
	public Comment addComment(@RequestBody Comment comment, @PathVariable("idTc") int idTc) {
		return commentService.addComment(comment, idTc);
	}
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
 	@DeleteMapping("/delete-comment/{commentid}")  
	public void deleteComment(@PathVariable("commentid") int commentid)    
	{  
		commentService.deleteComment(commentid);  
	} 
 	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
 	@PutMapping("/update-comment/{commentid}")  
	public String updateComment(@RequestBody Comment comment , @PathVariable("commentid")int commentid)    
	{  
		return commentService.updateComment(comment ,commentid);  			  
	}
 	
	@PreAuthorize("permitAll()")
 	@GetMapping("/get-all-comments")  
	public List<Comment> getAllComments()   
	{  
		return commentService.getAllComments();  
	}  
 	
	
 	@GetMapping("/get-own-comments")  
	public List<Comment> getOwnComments()  
	{  
		return commentService.getOwnComments(); 
	}
 	
 	@GetMapping("/detail-comment/{commentid}")  
	public Comment getComment(@PathVariable("commentid") int commentid)   
	{  
		return commentService.getCommentById(commentid);  
	}  
 	
 	
 	@GetMapping("/comments-by-user/{idU}")
	public List<Comment> getCommentsByUser(@PathVariable("idU") int idU) {
		return commentService.getCommentsByUserId(idU);

	}
 	
	@PreAuthorize("permitAll()")
 	@GetMapping("/count-all-comments")
	public int getcommentscount() {
		return commentService.countComments();
	}
	@PreAuthorize("permitAll()")
 	@GetMapping("/count-user-comments/{idU}")
	public int getusercommentscount(@PathVariable("idU") int idU) {
		return commentService.countCommentsByUser(idU);
	}
 	
	@PreAuthorize("permitAll()")
 	@GetMapping("/comments-by-Tc/{idTc}")
	public List<Comment> getCommentsByTC(@PathVariable("idTc") int idTc) {
		return commentService.getCommentsByTCId(idTc);

	}
 
 	
	@PreAuthorize("permitAll()")
	@GetMapping("/count-Tc-comments/{idTc}")
	public int getpostcommentscount(@PathVariable("idTc") int idTc) {
		return commentService.countCommentsByTC(idTc);
	}
	@PreAuthorize("permitAll()")
	@GetMapping("/Comment/search/")
	public List<Comment> commentSearch(@RequestParam("pattern")String pattern){
 
		return commentService.searchComments(pattern);
	
	}

}
