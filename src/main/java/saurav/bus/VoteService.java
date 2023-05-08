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

    public void upVote(Proposal proposal, User user) {
        Vote vote = voteFacade.findVoteByUserAndProposal(user, proposal);
        if (vote == null) {
            vote = new Vote(proposal, user, Vote.VoteChoice.FOR);
            voteFacade.create(vote);
        } else if (vote.getVoteChoice() != Vote.VoteChoice.FOR) {
            vote.setVoteChoice(Vote.VoteChoice.FOR);
            voteFacade.edit(vote);
        }
    }

    public void downVote(Proposal proposal, User user) {
        Vote vote = voteFacade.findVoteByUserAndProposal(user, proposal);
        if (vote == null) {
            vote = new Vote(proposal, user, Vote.VoteChoice.AGAINST);
            voteFacade.create(vote);
        } else if (vote.getVoteChoice() != Vote.VoteChoice.AGAINST) {
            vote.setVoteChoice(Vote.VoteChoice.AGAINST);
            voteFacade.edit(vote);
        }
    }

    public int countVotesByChoice(Proposal proposal, Vote.VoteChoice voteChoice) {
        return voteFacade.countVotesByChoice(proposal, voteChoice);
    }

    public Vote updateVote(Vote vote) {
        return voteFacade.edit(vote);
    }

    public void deleteVote(Vote vote) {
        voteFacade.remove(vote);
    }

    public Vote findVote(Object id) {
        return voteFacade.find(id);
    }

    public List<Vote> findAllVotes() {
        return voteFacade.findAll();
    }
}
