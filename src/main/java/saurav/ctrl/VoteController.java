/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import saurav.bus.ProposalService;
import saurav.bus.UserService;
import saurav.bus.VoteService;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.ents.Vote;

/**
 *
 * @author saura
 */
@Named
@ViewScoped
public class VoteController implements Serializable {

    @EJB
    private VoteService voteService;
    @EJB
    UserService userService;
    @EJB
    ProposalService proposalService;

    private int upVotes;
    private int downVotes;

    public VoteController() {
    }

    private User getUserFromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            return userService.findUserById(userId);
        } else {
            return null;
        }
    }

    public String voteUp(int proposalId) {
        // Fetch proposal and user object
        Proposal proposal = proposalService.findProposalById(proposalId);
        User user = getUserFromSession();

        System.out.println("IN VOTE UP METHOD");
        System.out.println("proposal id: " + proposalId);
        System.out.println(" User: null" + user.getUsername());
        voteService.vote(proposal, user, Vote.VoteChoice.UP_VOTE);

        System.out.println("Up Votes: " + showUpVotes(proposal));
        return "";
    }

    public String voteDown(int proposalId) {
        // Fetch proposal and user object
        Proposal proposal = proposalService.findProposalById(proposalId);
        User user = getUserFromSession();

        System.out.println("IN VOTE UP METHOD");
        System.out.println("proposal id: " + proposalId);
        System.out.println(" User: null" + user.getUsername());

        voteService.vote(proposal, user, Vote.VoteChoice.DOWN_VOTE);
        setUpVotes(voteService.countUpVotes(proposal));
        setDownVotes(voteService.countDownVotes(proposal));

        System.out.println("Down Votes: " + showDownVotes(proposal));
        return "";
    }

    public int showUpVotes(Proposal proposal) {
        return voteService.countUpVotes(proposal);
    }

    public int showDownVotes(Proposal proposal) {
        return voteService.countDownVotes(proposal);
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

}
