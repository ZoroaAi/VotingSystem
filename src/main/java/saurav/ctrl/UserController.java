/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import saurav.bus.UserService;
import saurav.ents.User;

/**
 *
 * @author saura
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private UserService userService;

    private User user;
    private String email;
    private String password;

    public UserController() {
        user = new User();
    }

    public String register() {
        if (user.isValid()) {
            userService.registerUser(user);
            // Navigate to a success page after successful registration
            return "/pages/index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid user data provided."));
            return null;
        }
    }

    public String login() {
        System.out.println("Authenticating user with email: " + email);
        User authenticatedUser = userService.authenticate(email, password);
        if (authenticatedUser != null) {
            // Set the authenticated user in the session
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            session.setAttribute("userId", authenticatedUser.getId());

            System.out.println("Session contents after login: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().toString());
            return "/pages/index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password", null));
            return null;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "/pages/login?faces-redirect=true";
    }

    public String submitForm() {
        System.out.println("Form submitted with email: " + this.getEmail());
        System.out.println("Form submitted with password: " + this.getPassword());
        return null;
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId") != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
