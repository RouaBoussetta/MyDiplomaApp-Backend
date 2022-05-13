package tn.uae.nawanera.spring.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

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
public class Assessment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "trainer")
	private User trainer;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "intern")
	private User intern;

	@Enumerated(EnumType.STRING)
	private Notice finalNotice;
	@NotBlank
	@Column(columnDefinition = "TEXT")
	private String remark;
	@Column(columnDefinition = "TEXT")

	private String signature;
	@Column(columnDefinition = "TEXT")

	private String stamp;
	
	

}
