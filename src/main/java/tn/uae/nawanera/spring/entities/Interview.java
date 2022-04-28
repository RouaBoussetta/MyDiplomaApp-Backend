package tn.uae.nawanera.spring.entities;

 
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
 
	@JsonFormat(pattern = "YYYY-MM-dd")
 	private Date interviewDate;
	@JsonFormat(pattern="HH:mm:ss")
 	private LocalTime interviewTime;
 	
	@OneToOne(cascade = CascadeType.ALL)
	private Application application;
	

}
