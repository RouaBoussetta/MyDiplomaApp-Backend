package tn.uae.nawanera.spring.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.uae.nawanera.spring.entities.Package;
import tn.uae.nawanera.spring.entities.Type;
import tn.uae.nawanera.spring.services.IPackageService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
 
public class PackageServiceTest {

	
	@Autowired
	IPackageService packageService;
	
	Package p;

	
	@Before
	public void setUp() {

 p=new Package();
 p.setId(1);
 p.setName("ikjkij");
 p.setPrice(250);
 p.setType(Type.MONTH);
 
 packageService.addPackage(p);
	assertThat(p).isNotNull();
	}
	

	
	@Test
	public void testGetAllPackages() {

		List<Package> packs = packageService.getAllPackages();
		assertThat(packs.size()).isNotZero();
	}
	
	
	@Test
	public void testUpdatePackage() {

		 p = packageService.getPackageDetails(1);
		 p.setName("updated");
		 Package pa=packageService.updatePackage(p);
		assertThat(p.getId()).isEqualTo(pa.getId());
	}
	
	
	@Test
	public void testGetPackageDetails() {

		 p = packageService.getPackageDetails(1);
		 
		assertThat(p).isNotNull();
	}
	
	
	@Test
	public void testRemovePackage() {

		 packageService.removePackage(2);
			List<Package> packs = packageService.getAllPackages();

		 
		 assertThat(packs.size()).isEqualTo(1);
	}
	
	

}
