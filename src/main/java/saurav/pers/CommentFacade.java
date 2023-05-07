/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.pers;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import saurav.ents.Comment;

/**
 *
 * @author saura
 */
@Stateless
public class CommentFacade extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
        System.out.println("Comment Facade constructor called");
    }

    public List<Comment> findCommentsForProposal(int proposalId) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.proposal.id = :proposalId", Comment.class)
             .setParameter("proposalId", proposalId)
             .getResultList();
    }
}
