package tn.uae.nawanera.spring.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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


public class User {
	
 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank
	@Size(max = 20)
	private String firstname;
	@NotBlank
	@Size(max = 20)
	private String lastname;
	@NotBlank
	@Size(max = 20)
	private String username;
	@NotBlank
	@Size(max = 20)
	private String companyName;
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	@Column(columnDefinition = "TEXT") 
	private String bio;
	 
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

	@NotBlank
	@Size(max = 120)
	private String password;
	 
	@Column(columnDefinition = "TEXT")
	private String userImage;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int trialPeriod;
	 
	@Column( columnDefinition = "boolean default false")
	private boolean subscribed;
	
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "role")
	private Role role;
	 
	@Column(name = "addedBy")
	private String addedBy;
	@JsonIgnore
	@Column(name = "trainedBy")
	private String trainedBy;
	@JsonIgnore
	@ToString.Exclude
	@Column(name = "accountLocked", columnDefinition = "boolean default false")
	private boolean accountLocked;
	@JsonIgnore
	@ToString.Exclude
	@Column(name = "failedAttempt", columnDefinition = "int default 0")
	private int failedAttempt;
	@JsonIgnore
	@ToString.Exclude
	@Column(name = "lockTime")
	private Date lockTime;
	@JsonIgnore
	@ToString.Exclude
	@Column(name = "resettoken")
	private String resettoken;
	@JsonIgnore
	@ToString.Exclude
 	private LocalDate activatedAt;
	 
	boolean valid;
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
	private Set<Subscription> subscriptions;
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", fetch = FetchType.EAGER)
	private Set<SkillAssessment> skillAssessments;
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "intern")
	private Set<Application> applications;
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "trainer")
	private Set<Assessment> assessments;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "intern")
	private Set<Assessment> internAssessments;
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(targetEntity = Vacancy.class, mappedBy = "postedby")
	private Set<Vacancy> vacancies;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "trainer")
	private Set<Project> projects;
	
	
	@ToString.Exclude
	//@JsonIgnore
	@ManyToMany
    @JoinTable( name = "Interns_projects",
                joinColumns = @JoinColumn( name = "idIntern" ),
                inverseJoinColumns = @JoinColumn( name = "idProject" ) )
    private List<Project> internProjects ;
	
	@ToString.Exclude
	 @JsonIgnore
	// @JsonProperty(access = JsonProperty.Access.READ_ONLY)

	@OneToMany(targetEntity = Certificate.class, mappedBy = "hrmanager")
	private List<Certificate> certificates;
	
	@ToString.Exclude
	 @JsonIgnore
	// @JsonProperty(access = JsonProperty.Access.READ_ONLY)

	@OneToMany(targetEntity = Certificate.class, mappedBy = "intern")
	private Set<Certificate> internCertificates;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sender", fetch = FetchType.EAGER)
	private Set<Notification> notifSend;
	
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver", fetch = FetchType.EAGER)
	private Set<Notification> notifReceive;
	
	
	@ToString.Exclude
	@JsonIgnore
	@ManyToMany
    @JoinTable( name = "Interns_Tasks",
                joinColumns = @JoinColumn( name = "idUser" ),
                inverseJoinColumns = @JoinColumn( name = "idTask" ) )
    private Set<Task> tasks ;

	private boolean affected;

	 
	
	
	


	 
	 
 
	
	

}
