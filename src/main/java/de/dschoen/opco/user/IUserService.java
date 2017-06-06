package de.dschoen.opco.user;

import java.util.List;

public interface IUserService {
     
	List<User> getAllUsers();
	User getUserById(int id);
	User getUserByLogin(String login);
	boolean addUser(User user);
	void updateUser(User user);
	void deleteUser(int userId);
	
	User login(User user);
	void init();
} 