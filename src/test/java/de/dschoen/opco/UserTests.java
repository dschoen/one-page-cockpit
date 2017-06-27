package de.dschoen.opco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.dschoen.opco.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	private final String BASE_URL = "http://localhost:8090/opco/api/users/"; 
		
	@Test
	public void getUserByIdTest() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class, 1);
        User user = responseEntity.getBody();
        
        assertEquals("getUserById: ", "Karl", user.getFirstname());        
    }
	
	@Test
	public void getAllUsersTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    RestTemplate restTemplate = new RestTemplate();
		String url = BASE_URL;
	    
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
	    ResponseEntity<User[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User[].class);
	    
	    User[] users = responseEntity.getBody();
	    
	    boolean size = users.length > 0 ? true : false;
	    
	    assertTrue(size);
	}
	
//	@Test
//	public void addUserTest() {
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//		String url = BASE_URL;
//		
//		User user = new User();
//        user.setLogin("homer");
//        user.setFirstname("Homer");
//        user.setLastname("Simpson");
//        user.setEmail("homer@simpson.de");		
//		
//		HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
//        URI uri = restTemplate.postForLocation(url, requestEntity);    	
//    }
	



//	
//	@Test
//    public void updateUserDemo() {
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/user/article";
//		User objUser = new User();
//		objUser.setUserId(1);
//		objUser.setLogin("Update:Java Concurrency");
//        HttpEntity<User> requestEntity = new HttpEntity<User>(objUser, headers);
//        restTemplate.put(url, requestEntity);
//    }
//	
//	@Test
//    public void deleteUserDemo() {
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/user/article/{id}";
//        HttpEntity<User> requestEntity = new HttpEntity<User>(headers);
//        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);        
//    }
    
}