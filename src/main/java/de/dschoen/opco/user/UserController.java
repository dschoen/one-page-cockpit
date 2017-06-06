package de.dschoen.opco.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/api/")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	
	// ----------------------------------------------------
	
	@GetMapping("users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {	
		User user = userService.getUserById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@GetMapping("users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> list = userService.getAllUsers();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@PostMapping("users")
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder) {
        boolean result = userService.addUser(user);
        if (result == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        User newUser = userService.getUserByLogin(user.getUsername());
        headers.setLocation(builder.path("/users/{id}").buildAndExpand(newUser.getUserId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	// ----------------------------------------------------
	
	@PutMapping("users")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@DeleteMapping("users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	// ----------------------------------------------------
	
	@PostMapping("users/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		
		User usr = userService.login(user);	
		
		//check UserPassword
		if (usr != null) {			
			//TODO Sicherheitstoken zurückgeben			
			return new ResponseEntity<User>(usr, HttpStatus.OK);
		} else {
			logger.warn("Bad Login Request: " + user.getUsername());
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}		
	}
}  