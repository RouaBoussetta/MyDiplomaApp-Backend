package tn.uae.nawanera.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import tn.uae.nawanera.spring.entities.Subscription;
import tn.uae.nawanera.spring.services.ISubscriptionService;

 
@RestController
@RequestMapping("/api/subscription")

public class SubscriptionController {
	@Autowired
	ISubscriptionService subscriptionService;
	
	
 		@PostMapping(value="/addCustomer/{idUser}")
	    public void createCustomer(@PathVariable("idUser")int idUser)  {
			subscriptionService.createStripeCustomer(idUser);
	    }
			
	 
		
		@PostMapping(value="/chargeCustomer/{customerId}/{amount}")
		public void chargeCustomer(@PathVariable("customerId")String customerId,@RequestBody Subscription p,@PathVariable("amount")int amount) throws StripeException  {
			subscriptionService.chargeCustomer(customerId,p,amount);
		}
			
}
