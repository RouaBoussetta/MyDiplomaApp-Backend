package tn.uae.nawanera.spring.repositories;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.User;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer>{

	List<Notification> findBySender(User sender);

	List<Notification> findByReceiver(User receiver);

 

}
