/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.pers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import saurav.ents.Proposal;
import saurav.ents.User;
import saurav.ents.Vote;

/**
 *
 * @author saura
 */
@Stateless
public class VoteFacade extends AbstractFacade<Vote> {

    @PersistenceContext(unitName = "persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VoteFacade() {
        super(Vote.class);
    }

    public Vote findVoteByUserAndProposal(User user, Proposal proposal) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vote> cq = cb.createQuery(Vote.class);
            Root<Vote> vote = cq.from(Vote.class);
            cq.where(cb.and(
                    cb.equal(vote.get("user"), user),
                    cb.equal(vote.get("proposal"), proposal)
            ));
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int countVotesByChoice(Proposal proposal, Vote.VoteChoice voteChoice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Vote> vote = cq.from(Vote.class);
        cq.select(cb.count(vote))
                .where(cb.and(
                        cb.equal(vote.get("proposal"), proposal),
                        cb.equal(vote.get("voteChoice"), voteChoice)));
        Long count = em.createQuery(cq).getSingleResult();
        return count != null ? count.intValue() : 0;
    }

}
