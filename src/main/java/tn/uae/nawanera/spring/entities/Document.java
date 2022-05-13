package tn.uae.nawanera.spring.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private String doc;
	
	//@JsonIgnore
	 @ManyToOne 
	 @JoinColumn(name="project", nullable=false)
	    private Project project;
	 
	 
	 

}
