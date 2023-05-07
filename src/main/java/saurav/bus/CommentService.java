/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import saurav.ents.Comment;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.pers.CommentFacade;

/**
 *
 * @author saura
 */
@Stateless
public class CommentService {

    @EJB
    private CommentFacade commentFacade;

    public void createComment(Comment comment, User user, Proposal proposal) {
        System.out.println("In CommentService.createComment() method");
        comment.setUser(user);
        comment.setProposal(proposal);
        System.out.println("Creating Comment with content: " + comment.getContent());
        System.out.println("User: " + user.getUsername());
        System.out.println("Proposal: " + proposal.getRuleTitle());
        System.out.println("Comment Facade: " + commentFacade);
        commentFacade.create(comment);
        System.out.println("Comment ID after create: " + comment.getId());
    }

    public List<Comment> findCommentsForProposal(int proposalId) {
        return commentFacade.findCommentsForProposal(proposalId);
    }

    public Comment updateComment(Comment comment) {
        if (comment == null || comment.getContent() == null || comment.getContent().isEmpty()) {
            throw new IllegalArgumentException("Invalid comment data provided.");
        }

        commentFacade.edit(comment);
        return comment;
    }

    public void deleteComment(int commentId) {
        Comment comment = commentFacade.find(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("Comment not found.");
        }

        commentFacade.remove(comment);
    }

    public Comment findComment(int commentId) {
        return commentFacade.find(commentId);
    }

    public List<Comment> findAllComments() {
        return commentFacade.findAll();
    }

    public List<Comment> getCommentsForProposal(Proposal proposal) {
        return commentFacade.findCommentsForProposal(proposal.getId());
    }
}
