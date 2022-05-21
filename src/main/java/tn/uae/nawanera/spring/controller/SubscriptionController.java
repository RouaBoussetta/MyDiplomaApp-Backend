package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import tn.uae.nawanera.spring.entities.Subscription;
import tn.uae.nawanera.spring.services.ISubscriptionService;
import tn.uae.nawanera.spring.services.IUserservice;

 
@RestController
@RequestMapping("/api/subscription")

public class SubscriptionController {
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IUserservice iuserService;
	
 		@PostMapping(value="/addCustomer/{idUser}")
	    public void createCustomer(@PathVariable("idUser")int idUser)  {
			subscriptionService.createStripeCustomer(idUser);
	    }
			
	 
 		@PreAuthorize(" hasAuthority('COMPANY')")

		@PostMapping(value="/chargeCustomer/{customerId}")
		public void chargeCustomer(@PathVariable("customerId")String customerId,@RequestBody Subscription p) throws StripeException  {
			subscriptionService.chargeCustomer(customerId,p);
		}
		
		@GetMapping(value="/find-payment_by-company/{id}")
		public List<Subscription> findByCompany(@PathVariable("id")int id) {
			return subscriptionService.findbyCompany(id);
		}
		
 
			
}
