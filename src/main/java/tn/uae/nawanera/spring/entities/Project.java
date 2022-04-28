package tn.uae.nawanera.spring.entities;

 
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Size(min = 2, max = 100, message = "The title must be between 2 and 100 messages.")
	@NotNull(message = "Please provide a title for the project")
	private String title;
	
	@Size(min = 10, message = "The title must contain 10 characters min")
	@NotNull(message = "Please provide a description for the project")
	private String description;
	
	private LocalDate   addedOn;
	private LocalTime   addedAt;
	
	private LocalDate   updatedOn;
	private LocalTime   updatedAt;
	
	
	@OneToOne
	private Vacancy vacancy;
	 @ManyToOne
	 @JoinColumn( name="trainer",nullable=false)
	    private User trainer;
	 @JsonIgnore
	 @ManyToMany
		@JoinTable(name = "Interns_projects", joinColumns = @JoinColumn(name = "idProject"), inverseJoinColumns = @JoinColumn(name = "idIntern"))
		private List<User> interns  ;
 


	 @OneToMany( targetEntity=Task.class, mappedBy="project" )
	    private List<Task> tasks ;
	 
	 @OneToMany( targetEntity=Document.class, mappedBy="project" )
	    private List<Document> documents ;
	 @JsonIgnore

	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
		private Set<TrainingCourse> courses;
	 @JsonIgnore
		@ToString.Exclude
	 @OneToMany( targetEntity=Meeting.class, mappedBy="project" )
	    private List<Meeting> meetings ;
	
}
