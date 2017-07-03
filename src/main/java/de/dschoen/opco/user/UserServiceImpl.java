package de.dschoen.opco.user;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDAO userDAO;
	
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
                if (userDAO.existsUser(user.getUsername())) {
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
	
	// ----------------------------------------------------
	
	@Override
	public User login(User user) {
		
		// get full user
		User usr = this.getUserByLogin(user.getUsername());
		
		// if user does not exist return null
		if (usr == null) {
			return null;
		}
		
		// check password		
		if (usr.getPassword().equals(user.getPassword())) {
			usr.setLastLogin(Instant.now());
			userDAO.updateUser(usr);
			return usr;
		}
		
		//else
		return null;					
	}
	
	// ----------------------------------------------------
	
	@Override
	public void init() {
		//check if Admin exists, else create
//		if (userDAO.existsUser("admin")) {
//			logger.info("User 'admin' already exists.");
//			return;
//		} 
//
//		logger.info("User 'admin' does not exist, therefore default 'admin'-'admin' is created.");
//		
//		User admin = new User();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setFirstname("Havelock");
//		admin.setLastname("Vetinari");
//		admin.setEmail("none@mail.com");
//		
//		
//		Board board = new Board("DemoBoard");
//		
//		Collection<Row> rows = board.getBoardRows();
//		rows.add(new Row("high"));
//		rows.add(new Row("medium"));
//		rows.add(new Row("low"));		
////		board.setBoardRows(rows);
//		
//		Collection<Column> cols = board.getBoardColumns();
//		cols.add(new Column("Todo"));
//		cols.add(new Column("inProgress"));
//		cols.add(new Column("Backlog"));
//		
//		admin.getBoards().add(board);
//		
//		userDAO.addUser(admin);
	}
} 