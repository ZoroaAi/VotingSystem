/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import saurav.bus.CommentService;
import saurav.ents.Comment;
import saurav.ents.Proposal;
import saurav.ents.User;

/**
 *
 * @author saura
 */
@Named(value = "commentController")
@RequestScoped
public class CommentController implements Serializable {

    @Inject
    private CommentService commentService;
    @Inject
    private UserController userController;
    @Inject
    private ProposalController proposalController;

    private Comment newComment = new Comment(); // Create a new Comment object

    public String addComment() {
        // Get the authenticated user from the session map
        FacesContext context = FacesContext.getCurrentInstance();
        User user = (User) context.getExternalContext().getSessionMap().get("user");

        // Get the current proposal from the proposalController
        Proposal proposal = proposalController.getProposal();

        // Set the user and proposal for the new comment
        newComment.setUser(user);
        newComment.setProposal(proposal);

        // Create the comment using the commentService
        commentService.createComment(newComment);

        // Reset the newComment object for the next comment
        newComment = new Comment();

        // Redirect to the same page to refresh the comments
        return "view_proposal?faces-redirect=true&proposalId=" + proposal.getId();
    }

    public Comment getNewComment() {
        return newComment;
    }

    public void setNewComment(Comment newComment) {
        this.newComment = newComment;
    }
}
