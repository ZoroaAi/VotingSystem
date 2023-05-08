/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saurav.ents;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author saura
 */
@Entity
@Table(name = "votes")
public class Vote implements Serializable {

    public enum VoteChoice {
        UP_VOTE,
        DOWN_VOTE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_choice")
    private VoteChoice voteChoice;

    // No-argument constructor (required by JPA)
    public Vote() {
    }

    // Constructor with arguments (optional, for convenience)
    public Vote(Proposal proposal, User voter, VoteChoice voteChoice) {
        this.proposal = proposal;
        this.user = voter;
        this.voteChoice = voteChoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteChoice getVoteChoice() {
        return voteChoice;
    }

    public void setVoteChoice(VoteChoice voteChoice) {
        this.voteChoice = voteChoice;
    }
}
