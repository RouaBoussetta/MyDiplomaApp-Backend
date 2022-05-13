package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

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
public class Certificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank
	private String title;
	@Column(columnDefinition = "TEXT")

	private String signature;
	@Column(columnDefinition = "TEXT")

	private String stamp;
	@NotBlank
	@Column(columnDefinition = "TEXT")
	private String description;
	private LocalDateTime attributedAt;
	private LocalDateTime downloadedAt;

	@ManyToOne

 
	@JoinColumn(name = "intern", nullable = false)
	private User intern;

	@ManyToOne
 
	@JoinColumn(name = "hrmanager", nullable = false)
	private User hrmanager;
}
