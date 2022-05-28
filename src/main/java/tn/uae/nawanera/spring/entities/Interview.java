package tn.uae.nawanera.spring.entities;

 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
 
import java.util.Date;
 

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

 

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Interview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdAt;
 
 	private LocalDate interviewDate;
	@JsonFormat(pattern="HH:mm:ss")
 	private LocalTime interviewTime;
	
	
 	private LocalDate endDate;
	@JsonFormat(pattern="HH:mm:ss")
 	private LocalTime endTime;
 	
	@OneToOne(cascade = CascadeType.ALL)
	private Application application;
	

}
