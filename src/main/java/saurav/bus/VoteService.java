/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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

    public Vote createVote(Vote vote) {
        voteFacade.create(vote);
        return vote;
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
