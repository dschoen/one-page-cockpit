package de.dschoen.opco.user;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDAO implements IUserDAO {
				
	@PersistenceContext	
	private EntityManager entityManager;	
	
	// ----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		String hql = "FROM User as usr ORDER BY usr.userId";
		return (List<User>) entityManager.createQuery(hql).getResultList();
	}

	// ----------------------------------------------------
	
	@Override
	public User getUserById(int id) {
		return entityManager.find(User.class, id);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return User or null if user not found
	 */
	@Override
	public User getUserByLogin(String login) {
		String hql = "FROM User as usr WHERE usr.username = ?";
		try { 
			User user =  (User) entityManager.createQuery(hql).setParameter(1, login).getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
	}	

	// ----------------------------------------------------
	
	@Override
	public void addUser(User user) {
		
		user.setCreateDate(LocalDateTime.now());
		entityManager.persist(user);		
	}

	// ----------------------------------------------------
	
	@Override
	public void updateUser(User user) {
		User usr = getUserById(user.getUserId());
		usr.setUsername(user.getUsername());		
		usr.setFirstname(user.getFirstname());
		usr.setLastname(user.getLastname());
		usr.setEmail(user.getEmail());
		usr.setPassword(user.getPassword());
		entityManager.flush();
		
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteUser(int id) {
		entityManager.remove(getUserById(id));
		
	}

	// ----------------------------------------------------
	
	@Override
	public boolean existsUser(String login) {
		String hql = "FROM User as usr WHERE usr.username = ?";
		int count = entityManager.createQuery(hql).setParameter(1, login).getResultList().size();
		return count > 0 ? true : false;
	}
}