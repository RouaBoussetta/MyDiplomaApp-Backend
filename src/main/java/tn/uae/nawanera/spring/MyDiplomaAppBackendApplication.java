package tn.uae.nawanera.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class MyDiplomaAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDiplomaAppBackendApplication.class, args);
	}

}
