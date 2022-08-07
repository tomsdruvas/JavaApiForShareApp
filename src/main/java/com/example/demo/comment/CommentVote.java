package com.example.demo.comment;

import com.example.demo.investor.Investor;
import com.example.demo.utils.VoteEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
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
