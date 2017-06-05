package de.dschoen.opco.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	
	// ----------------------------------------------------
	
	@Override
	public User getUserById(int userId) {
		User obj = userDAO.getUserById(userId);
		return obj;
	}	
	
	// ----------------------------------------------------
	
	@Override
	public User getUserByLogin(String login) {
		User usr = userDAO.getUserByLogin(login);
		return usr;
	}	
	
	// ----------------------------------------------------
	
	@Override
	public List<User> getAllUsers(){
		return userDAO.getAllUsers();
	}
	
	// ----------------------------------------------------
	
	@Override
	public synchronized boolean addUser(User user){
                if (userDAO.existsUser(user.getLogin())) {
    	            return false;
                } else {
    	            userDAO.addUser(user);
    	            return true;
                }
	}
	
	// ----------------------------------------------------
	
	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void deleteUser(int userId) {
		userDAO.deleteUser(userId);
	}
} 