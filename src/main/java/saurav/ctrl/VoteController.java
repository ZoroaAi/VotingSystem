/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package saurav.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
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

    public VoteController() {
    }

    public void voteUp(Proposal proposal, User user) {
//        System.out.println("Upvoting proposal " + proposal.getId() + " for user " + user.getUsername());
        voteService.upVote(proposal, user);
    }

    public void voteDown(Proposal proposal, User user) {
        voteService.downVote(proposal, user);
    }

    public int countUpVotes(Proposal proposal) {
//        System.out.println("In UpVote method");
        return voteService.countVotesByChoice(proposal, Vote.VoteChoice.FOR);
    }

    public int countDownVotes(Proposal proposal) {
//        System.out.println("In DownVote method");
        return voteService.countVotesByChoice(proposal, Vote.VoteChoice.AGAINST);
    }
}
