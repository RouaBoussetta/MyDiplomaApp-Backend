package tn.uae.nawanera.spring.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity

@AllArgsConstructor
@NoArgsConstructor

 @ToString

public class SkillAssessment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnore
	@OneToMany(mappedBy = "skillAssessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Application> applications;

	@Size(min = 2, max = 100, message = "The name must be between 2 and 100 messages.")
	@NotNull(message = "Please provide a name")
	private String title;

	@Size(max = 500, message = "The description can't be longer than 500 characters.")
	@NotNull(message = "Please, provide a description")
	private String description;

	@OneToMany(mappedBy = "skillAssessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions;
	
	
	private int score;
	
	private LocalDateTime createdAt;

	private Boolean isPublished = false;
	
	 
	@JsonIgnore
	@ManyToOne
	private User createdBy;

	
	 


	
	
	
	
	
	
	
	

}
