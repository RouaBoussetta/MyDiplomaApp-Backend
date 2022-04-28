package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
 

@Getter
@Setter
@Entity
public class Liking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 
	private int id;
	

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name= "trainingCourse")
	private TrainingCourse trainingCourse;
 

	@Column(name= "likeDate")
	private LocalDateTime likeDate;
	
	@ManyToOne
	@JoinColumn(name= "user")
	private User user;
}
