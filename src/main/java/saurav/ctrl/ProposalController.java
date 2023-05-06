/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import saurav.bus.ProposalService;
import saurav.bus.UserService;

import saurav.ents.Proposal;
import saurav.ents.User;

/**
 *
 * @author saura
 */
@Named
@RequestScoped
public class ProposalController implements Serializable {

    @EJB
    private ProposalService proposalService;
    @EJB
    private UserService userService;
    @Inject
    private UserController userController;

    private String commentContent;

    private Proposal proposal = new Proposal(); // Create a new Proposal object
    private String proposalId;

    public String submitProposal() {
        // Get the authenticated user from the session map
        FacesContext context = FacesContext.getCurrentInstance();
        User user = (User) context.getExternalContext().getSessionMap().get("user");

        // Pass the user and proposal object to the createProposal method
        proposalService.createProposal(proposal, user);

        // Redirect to the desired page
        return "index?faces-redirect=true";
    }

    public List<Proposal> getAllProposals() {
        return proposalService.findAllProposals();
    }

    // Used to load proposals in view_proposal.
    public void loadProposal(String proposalId) {
        System.out.println("Loading proposal with ID: " + proposalId);
        if (proposalService == null) {
            System.out.println("ProposalService is null");
        }
        if (userService == null) {
            System.out.println("UserService is null");
        }
        int id = Integer.parseInt(proposalId);
        this.proposal = proposalService.findProposal(id);
        if (this.proposal == null) {
            System.out.println("Proposal not found");
        } else {
            System.out.println("Proposal found: " + this.proposal.getRuleTitle());
            if (this.proposal.getUser() == null) {
                System.out.println("Proposal user is null");
            } else {
                System.out.println("Proposal user ID: " + this.proposal.getUser().getId());
                User user = userService.findUserById(this.proposal.getUser().getId());
                if (user == null) {
                    System.out.println("User not found in UserService");
                } else {
                    System.out.println("User found: " + user.getUsername());
                    this.proposal.setUser(user);
                }
            }
        }
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public ProposalService getProposalService() {
        return proposalService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }



}
