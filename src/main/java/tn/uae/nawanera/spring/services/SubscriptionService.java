package tn.uae.nawanera.spring.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.Subscription;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.INotificationRepository;
import tn.uae.nawanera.spring.repositories.SubscriptionRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;

 

@Service
public class SubscriptionService implements ISubscriptionService{
	private static final Logger logger = LogManager.getLogger(SubscriptionService.class);

	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	IUserservice iuserService;
	
	@Autowired
	SubscriptionRepository paymentRepository;
	@Autowired 
	INotificationRepository iNotificationRepository;
	
	String api = "sk_test_51KOJFmCACCgNbueC6H7eLyQPjGHs1vRJOXuo1UKRywpkRdCG8f36pHtfAQ3Hmy8FMPckFIvS9cmZvixEoBhMHQEa000UzFOren";

	@Override
	public void createStripeCustomer(int idcompany) {
		Stripe.apiKey =api;
		User company = userRepository.findById(idcompany);
		Map<String, Object> params = new HashMap<>();
		
		params.put("email", company.getEmail());
		params.put("description", "My First Test Customer");

		try {
		  Customer.create(params);
		} catch (StripeException e) {

			logger.info(e);
		}
		
	}
 
	@Override
	public String createCardForCustumorStripe(String customerId,Subscription p) throws StripeException {
		Stripe.apiKey =api;
		Customer customer = Customer.retrieve(customerId);
		Map<String, Object> cardParam = new HashMap<>();
		cardParam.put("number", p.getCard());
		cardParam.put("exp_month", p.getExpMonth());
		cardParam.put("exp_year", p.getExpYear());
		cardParam.put("cvc", p.getCvc());
		
		
		Map<String, Object> tokenParam = new HashMap<>();
		tokenParam.put("card", cardParam);

		Token token = Token.create(tokenParam);
		Map<String, Object> source = new HashMap<>();
		source.put("source", token.getId());

		customer.getSources().create(source);
		return token.getId();
	}

	
	@Override
	public void chargeCustomer(String customerId,Subscription p,int amount) throws StripeException  {
		Stripe.apiKey = api;
		User company=iuserService.currentUser();
		List<User> users=userRepository.findAllByCompanyName(company.getCompanyName());

		createCardForCustumorStripe(customerId,p) ;
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", amount);
		params.put("currency", "usd");
		params.put("customer", "cus_L4TboHTyXLiW7u");

		try {
			 Charge.create(params);
			
			p.setCard(p.getCard());
			p.setExpMonth(p.getExpMonth());
			p.setExpYear(p.getExpYear());
			p.setCvc(p.getCvc());
			p.setAmount(amount);
			paymentRepository.save(p);
			company.setSubscribed(true);
			userRepository.save(company);
			for(User u:users) {
				u.setSubscribed(true);
				userRepository.save(u);
			}
			
			addPaidSubscriptionNotif(p);
			
			
		} catch (StripeException e) {
			logger.info(e);
		}
		
	}
	
	@Override	
	  public Notification addPaidSubscriptionNotif (Subscription s)   {
		
		User sender=userRepository.findById(1);
		
		
		
		Notification notif = new Notification ();
		notif.setSubject("Dear "+iuserService.currentUser().getCompanyName()+", Your payment has been made successfully. Thank You !");
		notif.setDate(LocalDate.now());
		notif.setTime(LocalTime.now());
		 notif.setSender(sender);
		notif.setReceiver(iuserService.currentUser());
		iNotificationRepository.save(notif);
		return (notif);}

	
	
	@Override	
	  public Notification addSubscriptionNotif (Subscription s) {
		
		User sender=userRepository.findById(1);
		Notification notif = new Notification ();
		notif.setSubject("Dear "+s.getCompany().getUsername()+", You have finished your trial period, please pay to continue using our application");
		notif.setDate(LocalDate.now());
		notif.setTime(LocalTime.now());
		 notif.setSender(sender);
		notif.setReceiver(s.getCompany());
		iNotificationRepository.save(notif);
		return (notif);}

	
	
	 


}
