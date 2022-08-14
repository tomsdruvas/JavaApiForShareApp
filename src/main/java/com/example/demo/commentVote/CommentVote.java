package com.example.demo.commentVote;

import com.example.demo.comment.Comment;
import com.example.demo.investor.Investor;
import com.example.demo.utils.VoteEnum;
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

    @ManyToOne
    @NonNull
    private Investor investor;

    @ManyToOne
    @NonNull
    private Comment comment;

    @Column
    @NonNull
    private Enum<VoteEnum> voteDirection;


}
