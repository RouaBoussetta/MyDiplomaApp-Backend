package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 import tn.uae.nawanera.spring.entities.Subscription;
import tn.uae.nawanera.spring.entities.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription	,Integer>{

	List<Subscription> findByCompany(User company);

}
