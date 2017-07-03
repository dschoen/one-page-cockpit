package de.dschoen.opco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.dschoen.opco.board.services.BoardService;
import de.dschoen.opco.user.UserServiceImpl;

@SpringBootApplication
public class OpcoApplication {

	public static void main(String[] args) {		
				
		ConfigurableApplicationContext context = SpringApplication.run(OpcoApplication.class, args);
		
		// init
//		context.getBean(UserServiceImpl.class).init();
		context.getBean(BoardService.class).init();

	}
}
