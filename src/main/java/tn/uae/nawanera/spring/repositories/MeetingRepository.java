package tn.uae.nawanera.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uae.nawanera.spring.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting,Integer>{

}
