package tn.uae.nawanera.spring.services;
import java.util.List;

import tn.uae.nawanera.spring.entities.Package;
public interface IPackageService {
	
	Package addPackage(Package p);
	Package updatePackage(Package p);
	Package getPackageDetails(int id);
	List<Package> getAllPackages();
	void removePackage(int id);
	

}
