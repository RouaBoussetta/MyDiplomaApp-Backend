package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Comment;

 

public interface ICommentService {
	public Comment addComment(Comment c, int idTc) ;
	public String deleteComment(int id)  ;
	public String updateComment(Comment c, int id) ;
	public List<Comment> getAllComments();
	public Comment getCommentById(int id);
	public int countComments();
	public List<Comment> getCommentsByUserId(int id);
	public int countCommentsByUser(int id);
	public List<Comment> getCommentsByTCId(int id);
	public int countCommentsByTC(int id);
    public List<Comment> searchComments(String text);
 	public List<Comment> getOwnComments() ;
 	
}
