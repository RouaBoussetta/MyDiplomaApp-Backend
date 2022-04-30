package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.User;
 

public interface INotificationService {

 	public Notification   getNotifById(int id);

	public List<Notification > getAllNotif();

	public List<Notification > getNotifBySender(int id);
	public List<Notification > getNotifByReceiver(int id);
 
	public void deleteNotif(int id);
	public void deleteNotifbyUser(int idreceiver);

 
	public List<Notification > getMyNotifs()  ;
	Notification addNotification(User receiver, User sender, String subject, String description);
}
