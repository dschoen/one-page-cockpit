package de.dschoen.opco.user;

import java.util.List;

public interface IUserDAO {

    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByLogin(String login);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int userd);
    boolean existsUser(String name);
}
