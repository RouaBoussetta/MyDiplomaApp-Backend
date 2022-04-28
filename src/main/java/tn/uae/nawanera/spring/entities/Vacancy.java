package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
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
public class Vacancy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	@NotBlank
	@Size(max = 20)
	private String title;
	
	private Status status;
	private String companyName;
	@Enumerated(EnumType.STRING)
	private Degree degree;
	@NotBlank
	@Column(columnDefinition = "TEXT") 
	private String description;
	private Boolean paid;
	private float salary;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "category")
	private VacancyCategory category;
	
	//skill
	private String qualification;
	
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "postedby")
	private User postedby;
	
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "trainedby")
	private User trainedby;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy="vacancy")
	private Set<Application> applications;
	
  
	private LocalDateTime postedAt;
 
 
	private LocalDateTime updatedOn;
	
	

}
