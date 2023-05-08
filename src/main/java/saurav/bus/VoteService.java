/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.ents.Vote;
import saurav.pers.VoteFacade;

/**
 *
 * @author saura
 */
@Stateless
public class VoteService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private VoteFacade voteFacade;

    public void vote(Proposal proposal, User user, Vote.VoteChoice voteChoice) {
        Vote vote = new Vote(proposal, user, voteChoice);
        voteFacade.create(vote);
    }

    public int countUpVotes(Proposal proposal) {
        return (int) voteFacade.countVotesByChoice(proposal, Vote.VoteChoice.UP_VOTE);
    }

    public int countDownVotes(Proposal proposal) {
        return (int) voteFacade.countVotesByChoice(proposal, Vote.VoteChoice.DOWN_VOTE);
    }
}
