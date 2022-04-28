package tn.uae.nawanera.spring.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class VacancyCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	@NotBlank
	@Size(max = 20)
	private String category;

	private LocalDateTime addedAt;

	private LocalDateTime updatedAt;

	@OneToMany(targetEntity = Vacancy.class, mappedBy = "category")
	@ToString.Exclude
@JsonIgnore
	private Set<Vacancy> vacancy;
}
