package tn.uae.nawanera.spring.entities;

 

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Meeting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	
	 @ManyToOne 
	 @JoinColumn(name="project", nullable=false)
	    private Project project;

		@Size(min = 2, max = 50, message = "The meeting name must be between 2 and 50  .")
 		private String name;
		
		private String code;
		private LocalDateTime startedAt;

		private LocalDateTime endedAt;
}
