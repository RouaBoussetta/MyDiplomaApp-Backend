package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

 
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class TrainingCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@NotBlank
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)              
	private Type type;
 
	private LocalDateTime publishedAt;
 
	private LocalDateTime updatedAt;

	@NotBlank
	@Column(columnDefinition = "TEXT")
	private String course;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingCourse", fetch = FetchType.EAGER)
    @OrderBy("desc")
    private List<Comment> comments = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingCourse", fetch= FetchType.EAGER)
	private Set<Liking> likes = new HashSet<>();

	 private String publishedBy;
	 
	 @ManyToOne 
	 @JoinColumn(name="project", nullable=false)
	    private Project project;
	
	
}
