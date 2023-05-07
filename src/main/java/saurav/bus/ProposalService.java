/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package saurav.bus;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.pers.ProposalFacade;

/**
 *
 * @author saura
 */
@Stateless
public class ProposalService {

    @PersistenceContext(unitName = "persistence_unit")
    private EntityManager em;

    @EJB
    private ProposalFacade proposalFacade;

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

    public void deleteProposal(int proposalId) {
        Proposal proposal = em.find(Proposal.class, proposalId);
        if (proposal != null) {
            em.remove(proposal);
        }
    }

    public Proposal findProposal(Object id) {
        try {
            TypedQuery<Proposal> query = em.createQuery("SELECT p FROM Proposal p JOIN FETCH p.user WHERE p.id = :id", Proposal.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Proposal> findAllProposals() {
        return proposalFacade.findAll();
    }

}
