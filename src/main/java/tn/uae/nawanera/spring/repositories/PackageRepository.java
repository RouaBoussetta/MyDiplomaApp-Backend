package tn.uae.nawanera.spring.repositories;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer>{

	Package findById(int id);
}
