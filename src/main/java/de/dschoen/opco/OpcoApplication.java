package de.dschoen.opco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.dschoen.opco.demo.DemoService;
import de.dschoen.opco.user.UserService;

@SpringBootApplication
public class OpcoApplication {

	public static void main(String[] args) {		
				
		ConfigurableApplicationContext context = SpringApplication.run(OpcoApplication.class, args);
		
		// init
		context.getBean(UserService.class).init();
		
		// demo
		context.getBean(DemoService.class).createDemoData();
	}
}