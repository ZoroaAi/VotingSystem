/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.pers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import saurav.ents.Comment;

/**
 *
 * @author saura
 */
public class CommentFacade extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }

    public List<Comment> findCommentsForProposal(int proposalId) {
        TypedQuery<Comment> query = em.createQuery(
                "SELECT c FROM Comment c WHERE c.proposal.id = :proposalId", Comment.class);
        query.setParameter("proposalId", proposalId);
        return query.getResultList();
    }
}
