package tn.uae.nawanera.spring.repositories;

 
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.User;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer>{

	List<Notification> findBySender(User sender);

	List<Notification> findByReceiver(User receiver);

	 
	@Transactional
	@Modifying
	@Query("delete from Notification n where  n.receiver.id=:id")
 	void deleteAllByReceiver(@Param("id") int id);

 

}
