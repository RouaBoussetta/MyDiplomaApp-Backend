package tn.uae.nawanera.spring.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Notification {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
 	private int id;
 	private String subject;
 	private String description;
 	private LocalDate date;
 	private LocalTime time;

	@Column(name = "isRead")
	private boolean isRead;
	
	
 
	
	@ManyToOne
	private User sender;

	@ManyToOne
	private User receiver;
}
