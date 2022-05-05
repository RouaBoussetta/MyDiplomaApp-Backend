package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Comment;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.repositories.ICommentRepository;
import tn.uae.nawanera.spring.repositories.ITrainingCourseRepository;

@Service
public class CommentService implements ICommentService {
	@Autowired
	ITrainingCourseRepository itcRepository;

	@Autowired
	ICommentRepository iCommentRepository;

	@Autowired
	IUserservice iuserService;

	@Override
	public Comment addComment(Comment c, int idTc) {
		TrainingCourse tc = itcRepository.findById(idTc);
		c.setUser(iuserService.currentUser());
		c.setCommentedAt(LocalDateTime.now());
		c.setTrainingCourse(tc);
		iCommentRepository.save(c);
		return c;
	}

	@Override
	public String deleteComment(int id) {

		int iduser = iuserService.currentUser().getId();
		Comment c = iCommentRepository.findById(id) ;
		if (iduser==c.getUser().getId()){
		 
			iCommentRepository.deleteComment(id);
			 
		return ("Comment deleted successfully");
		}
		else{
		return ("You are not allowed to delete this comment");	
		}

	}

	@Override
	public String updateComment(Comment c, int id) {
		int iduser = iuserService.currentUser().getId();
		Comment comment = iCommentRepository.findById(id);
		if (iduser != comment.getUser().getId()) {
			return ("You are not allowed to update this comment");
		} else {
			comment.setCommentContent(c.getCommentContent());

			comment.setUpdatedAt(LocalDateTime.now());

			iCommentRepository.save(comment);
			return ("Comment updated successfully");
		}
	}

	@Override
	public List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<>();
		iCommentRepository.findAll().forEach(comments::add);
		return comments;
	}

	@Override
	public Comment getCommentById(int id) {
		return iCommentRepository.findById(id) ;
	}

	@Override
	public int countComments() {
		return iCommentRepository.findAll().size();
	}

	@Override
	public List<Comment> getCommentsByUserId(int id) {
		return iCommentRepository.getCommentsByUserId(id);
	}

	@Override
	public int countCommentsByUser(int id) {
		return iCommentRepository.getCommentsByUserId(id).size();
	}

	@Override
	public List<Comment> getCommentsByTCId(int id) {

		TrainingCourse tc = itcRepository.findById(id) ;
		return iCommentRepository.getCommentsByTrainingCourse(tc);
	}

	@Override
	public int countCommentsByTC(int id) {
		TrainingCourse tc = itcRepository.findById(id) ;

		return iCommentRepository.getCommentsByTrainingCourse(tc).size();
	}

	@Override
	public List<Comment> searchComments(String pattern) {
		return iCommentRepository.findCommentsByTextContaining(pattern);
	}

	@Override
	public List<Comment> getOwnComments() {
		return iCommentRepository.getCommentsByUserId(iuserService.currentUser().getId());

	}

}
