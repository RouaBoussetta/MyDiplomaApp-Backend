package tn.uae.nawanera.spring.controller;

 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Package;
 import tn.uae.nawanera.spring.services.PackageService;

 

@RestController
@RequestMapping("/api/package")
public class PackageController {

	@Autowired
	PackageService packageservice;
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PostMapping("/add")
	public Package addPackage(@RequestBody Package p) {
		return packageservice.addPackage(p);
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping("/update")
	public Package updatePackage(@RequestBody Package p) {
		return packageservice.updatePackage(p);
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')")
	@GetMapping(value = "/getAllPackages")
	@ResponseBody
	public List<Package> getAllPackages() {

		return packageservice.getAllPackages();
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')")
	@GetMapping(value = "/get-Package/{id}")
	@ResponseBody
	public Package  getPackage(@PathVariable("id") int id) {

		return packageservice.getPackageDetails(id);
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")

	@DeleteMapping("/delete-package/{id}")  
	public void deletePackage(@PathVariable("id") int id)   
	{  
		packageservice.removePackage(id);
	}
	
}
