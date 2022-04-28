package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Demo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Enumerated(EnumType.STRING)
	private DemoCategory category;
	@Column(columnDefinition = "TEXT")

	private String file;
	private LocalDateTime addedAt;

	private LocalDateTime updatedAt;
	@JsonIgnore
	 @ManyToOne 
	 
	    private Task task;
}
