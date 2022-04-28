package tn.uae.nawanera.spring.entities;

 
 

 
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	private int nbPack;
 
	private float amount;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime subscribedAt;
	private Date endsAt;
	
	
	private String card;
	private String expMonth;
	private String expYear;
	private String cvc;
	@ManyToOne
	@JoinColumn( name="idcompany" )
	private User company;
	
	@ManyToOne
	@JoinColumn( name="pack" )
	private Package pack;

}
