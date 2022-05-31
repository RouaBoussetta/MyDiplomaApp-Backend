package tn.uae.nawanera.spring.entities;

  
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Application {

  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
 	@ToString.Exclude
	@ManyToOne
	private User intern;
 
	@Column(columnDefinition = "TEXT")
	private String cv;
	
	private Boolean isAffected;
 	private Boolean interviewPlanned;
	
 	@ToString.Exclude
	@ManyToOne
	private Vacancy vacancy;

 	private Boolean done = false;
	@ManyToOne
	private SkillAssessment skillAssessment;

	private int internScore;
	
 	private LocalDate appliedOn;
	@JsonFormat(pattern="HH:mm:ss")
 	private LocalTime appliedAt;

	public Application(User intern, String cv) {
		super();
		this.intern = intern;
		this.cv = cv;
	}

	

	
	
	
	

}
