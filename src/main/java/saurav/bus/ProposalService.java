/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.pers.ProposalFacade;

/**
 *
 * @author saura
 */
@Stateless
public class ProposalService {

    @EJB
    private ProposalFacade proposalFacade;

    // Import any necessary classes/packages, such as User and Timestamp
    public Proposal createProposal(Proposal proposal, User user) {
        // Set initial proposal state, user, and timestamp
        proposal.setProposalState(Proposal.ProposalState.NEWLY_SUBMITTED);
        proposal.setUser(user);
        proposal.setTimePosted(new Timestamp(System.currentTimeMillis()));

        proposalFacade.create(proposal);
        return proposal;
    }

    public Proposal updateProposal(Proposal proposal) {
        return proposalFacade.edit(proposal);
    }

    public void deleteProposal(Proposal proposal) {
        proposalFacade.remove(proposal);
    }

    public Proposal findProposal(Object id) {
        Proposal proposal = proposalFacade.find(id);
        
        if (proposal != null && proposal.getUser() != null) {
            proposal.getUser().getId();
        }
        return proposal;
    }

    public List<Proposal> findAllProposals() {
        return proposalFacade.findAll();
    }
}
