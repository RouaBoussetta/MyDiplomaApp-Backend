package tn.uae.nawanera.spring.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.INotificationRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;

@Service
public class NotificationService implements INotificationService{
	
	
	@Autowired 
	INotificationRepository iNotificationRepository;
	
	@Autowired 
	UserRepository userRepo ;
	
	@Autowired 
	IUserservice iuserService ;
	
	@Override
	public Notification getNotifById(int id) {
		  return iNotificationRepository.findById(id);
	}

	@Override
	public List<Notification> getAllNotif() {
		List<Notification>notifs = new ArrayList<>();
		iNotificationRepository.findAll().forEach(notifs::add);
        return notifs;
	}

	@Override
	public List<Notification> getNotifBySender(int id) {
		
		User sender=userRepo.findById(id);
		 return iNotificationRepository.findBySender(sender);
	}
	
	@Override
	public List<Notification> getNotifByReceiver(int id) {
		
		User receiver=userRepo.findById(id);
		 return iNotificationRepository.findByReceiver(receiver);
	}

	@Override
	public void deleteNotif(int id) {
	       iNotificationRepository.deleteById(id);
		
	}

 

	@Override
	public List<Notification> getMyNotifs()  {
		return iNotificationRepository.findByReceiver(iuserService.currentUser());

	}

	@Override
	public void deleteNotifbyUser(int idreceiver) {
		 
		  iNotificationRepository.deleteAllByReceiver(idreceiver);
	}

	@Override
	public Notification addNotification(User receiver,User sender,String subject,String description) {
		Notification notification=new Notification();
		notification.setReceiver(receiver);
		notification.setSender(sender);
		notification.setDescription(description);
		notification.setSubject(subject);
		notification.setRead(false);
		notification.setDate(LocalDate.now());
		notification.setTime(LocalTime.now());
		
		return iNotificationRepository.save(notification);
	}

}
