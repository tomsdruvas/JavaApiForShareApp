package com.rateMyPortfolio.commentVote;

import com.rateMyPortfolio.comment.Comment;
import com.rateMyPortfolio.investor.Investor;
import com.rateMyPortfolio.utils.VoteEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;

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
    private Investor investor;

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
