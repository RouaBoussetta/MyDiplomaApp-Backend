package tn.uae.nawanera.spring.entities;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Package {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name ;
	@Enumerated(EnumType.STRING)
	private SubscriptionType type;
	 
	 
	private float price;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pack", fetch = FetchType.EAGER)
	private Set<Subscription> subscriptions;
}
