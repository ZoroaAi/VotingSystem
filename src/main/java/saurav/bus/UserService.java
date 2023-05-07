/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import saurav.ents.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import saurav.pers.UserFacade;

/**
 *
 * @author saura
 */
@Stateless
public class UserService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private UserFacade userFacade;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    public User createUser(User user) {
        // Prepare the user object for use (if necessary) and validate it
        if (user == null || !isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user data provided.");
        }

        // Persist the user
        userFacade.create(user);

        return user;
    }

    public User updateUser(User user) {
        // Check if the user object is valid
        if (user == null || !isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user data provided.");
        }

        // Update the user
        userFacade.edit(user);

        return user;
    }

    public void deleteUser(int userId) {
        // Check if the user exists
        User user = userFacade.find(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // Delete the user
        userFacade.remove(user);
    }

    public User findUser(int userId) {
        return userFacade.find(userId);
    }

    public User findUserById(int userId) {
        return userFacade.find(userId);
    }

    public List<User> findAllUsers() {
        return userFacade.findAll();
    }

    private boolean isValidUser(User user) {
        return user != null
                && user.getUsername() != null && !user.getUsername().isEmpty()
                && user.getEmail() != null && !user.getEmail().isEmpty();
    }

    public User registerUser(User user) {
        userFacade.create(user);
        LOGGER.log(Level.INFO, "User registered with email: {0}", user.getEmail());
        return user;
    }

    public User authenticate(String email, String password) {
        LOGGER.log(Level.INFO, "Authenticating user with email: {0}", email);
        System.out.println("IN authencation method");
        List<User> users = userFacade.findAll();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.checkPassword(password)) {
                LOGGER.log(Level.INFO, "User authenticated: {0}", user.getUsername());
                System.out.println("User authenticated: " + user.getUsername());
                System.out.println("User ID: " + user.getId());
                return user;
            }
        }
        LOGGER.log(Level.INFO, "Authenticating user with email: {0}", email);
        // Return null if no matching user is found
        return null;
    }
}
