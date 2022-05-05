package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.repositories.PackageRepository;
import tn.uae.nawanera.spring.entities.Package;
 @Service
public class PackageService implements IPackageService{

	@Autowired
	PackageRepository packageRepository;
	
	@Override
	public  Package addPackage(Package p) {
		 
		return packageRepository.save(p);
	}

	@Override
	public List<Package> getAllPackages() {
 		return packageRepository.findAll();
	}

	@Override
	public void removePackage(int id) {
	
		Package p=packageRepository.findById(id);
		
		packageRepository.delete(p);
		
	}

	@Override
	public Package updatePackage(Package p) {
		 
		Package pack = packageRepository.findById(p.getId()) ;
		 
		if (p.getName()==null)
			p.setName(pack.getName());
		else
			p.setName(p.getName());
		if (p.getType()==null)
			p.setType(pack.getType());
		else
			p.setType(p.getType());
		
		
		if (p.getPrice()==0)
			p.setPrice(pack.getPrice());
		else
			p.setPrice(p.getPrice());
			
			return packageRepository.save(p);
		 
	}

	@Override
	public Package getPackageDetails(int id) {
 
		return packageRepository.findById(id) ;
	}

 
	

}
