package com.rateMyPortfolio.commentVote;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.comment.Comment;
import com.rateMyPortfolio.utils.VoteEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueInvestorAndComment",
        columnNames = { "investor_id", "comment_id" }) })
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentVote {

    @Id

    @SequenceGenerator(
            name = "comment_vote_sequence",
            sequenceName = "comment_vote_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_vote_sequence"
    )
    private Long id;

    @Column(name = "investor_id")
    private Long investorId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "investor_id", insertable = false, updatable = false)
    @NonNull
    private ApplicationUser applicationUser;

    @Column(name = "comment_id")
    private Long commentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    @NonNull
    private Comment comment;

    @Column
    @NonNull
    private VoteEnum voteDirection;

    public CommentVote(Long investorId, Long commentId, @NonNull VoteEnum voteDirection) {
        this.investorId = investorId;
        this.commentId = commentId;
        this.voteDirection = voteDirection;
    }

    public CommentVote(Long id, Long investorId, Long commentId, @NonNull VoteEnum voteDirection) {
        this.id = id;
        this.investorId = investorId;
        this.commentId = commentId;
        this.voteDirection = voteDirection;
    }
}
