package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 2, max = 100, message = "The task name must be between 2 and 100  characteres.")
	@NotNull(message = "Please provide a task name of the project")
	private String taskName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	private LocalDateTime assignedAt;

	@JsonFormat(pattern = "YYYY-MM-dd")
	private Date deadline;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@Column(columnDefinition = "TEXT")
	private String taskIssue;

 
	@ToString.Include
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "project", nullable = false)
	private Project project;
	@JsonIgnore

	@ManyToMany
	@JoinTable(name = "Interns_Tasks", joinColumns = @JoinColumn(name = "idTask"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private Set<User> users  ;
	
	@JsonIgnore
	 @OneToMany(  mappedBy="task" )
	    private Set<Demo> demos ;
}
