package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Notification;
 

public interface INotificationService {

	
	public Notification   getNotifById(int id);

	public List<Notification > getAllNotif();

	public List<Notification > getNotifBySender(int id);
	public List<Notification > getNotifByReceiver(int id);

	public void deleteNotif(int id);

 
	public List<Notification > getMyNotifs()  ;
}
