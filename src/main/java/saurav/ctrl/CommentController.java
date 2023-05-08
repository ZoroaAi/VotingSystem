/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import saurav.bus.CommentService;
import saurav.bus.UserService;
import saurav.ents.Comment;
import saurav.ents.Proposal;
import saurav.ents.User;


/**
 *
 * @author saura
 */
@Named
@RequestScoped
public class CommentController implements Serializable {

    @Inject
    private CommentService commentService;
    @Inject
    private UserService userService;

    // Create a new Comment object
    private Comment newComment = new Comment();
    private List<Comment> commentsForProposal;

    public String addComment() {
        System.out.println("In Add Comment Method");
        // Get authenticated user Id from the session map
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("Session contents before getting user: " + context.getExternalContext().getSessionMap().toString());
        context.getExternalContext().getSession(true);
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        Integer userId = (Integer) sessionMap.get("userId");

        // Retrieve the user object from the database using the user ID
        User user = userService.findUserById(userId);

        if (user == null) {
            System.out.println("User not found in session");
        } else {
            System.out.println("User found in session: " + user.getUsername());
        }
        // Get current proposal from the proposalController
        Proposal proposal = (Proposal) context.getExternalContext().getSessionMap().get("currentProposal");
        System.out.println("New Comment: " + newComment);
        newComment.setUser(user);
        newComment.setProposal(proposal);
        try {
            commentService.createComment(newComment, user, proposal);
        } catch (EJBException e) {
            System.out.println("EJBException caught in addComment()");
            e.printStackTrace();
        }
        System.out.println("Comment Added:" + newComment.getContent() + " to Proposal: " + proposal.getRuleTitle() + " with ID: " + proposal.getId());
        // Reset
        newComment = new Comment();
        // Redirect to the same page to refresh the comments
        return null;
    }

    @PostConstruct
    public void init() {
        commentsForProposal = new ArrayList<>();
    }

    public Comment getNewComment() {
        return newComment;
    }

    public void setNewComment(Comment newComment) {
        this.newComment = newComment;
    }

    public List<Comment> getCommentsForProposal() {
        return commentsForProposal;
    }

    public void setCommentsForProposal(List<Comment> commentsForProposal) {
        this.commentsForProposal = commentsForProposal;
    }

    public void loadCommentsForProposal(int proposalId) {
        commentsForProposal = commentService.findCommentsForProposal(proposalId);
    }

}
