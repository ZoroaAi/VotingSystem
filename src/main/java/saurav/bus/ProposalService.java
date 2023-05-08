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
    @EJB
    private CommentService commentService;
    @EJB
    private UserService userService;

    public Proposal createProposal(Proposal proposal, User user) {
        // Set initial proposal state, user, and timestamp
        proposal.setProposalState(Proposal.ProposalState.NEWLY_SUBMITTED);
        proposal.setUser(user);
        proposal.setTimePosted(new Timestamp(System.currentTimeMillis()));

        System.out.println("User in createProposal" + user.getUsername() + "ID: " + user.getId());

        proposalFacade.create(proposal);

        user = userService.refreshUser(user);
        return proposal;
    }

    public Proposal updateProposal(Proposal proposal) {
        return proposalFacade.edit(proposal);
    }

    public void deleteProposal(int proposalId) {
        // Delete all associated comments
        commentService.deleteProposalWithComments(proposalId);

        // Delete proposal
        Proposal proposal = proposalFacade.find(proposalId);
        if (proposal == null) {
            System.out.println("Proposal no found with Id: " + proposalId);
        } else {
            proposalFacade.remove(proposal);
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

    public List<Proposal> findProposalByTitle(String title) {
        TypedQuery<Proposal> query = em.createQuery("SELECT p FROM Proposal p WHERE p.ruleTitle LIKE :title", Proposal.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    public List<Proposal> findAllProposals() {
        return proposalFacade.findAll();
    }

}
