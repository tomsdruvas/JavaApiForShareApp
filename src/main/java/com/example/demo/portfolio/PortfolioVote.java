package com.example.demo.portfolio;


import com.example.demo.comment.Comment;
import com.example.demo.investor.Investor;
import com.example.demo.utils.VoteEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueInvestorAndComment",
        columnNames = { "investor_id", "portfolio_id" }) })
@Getter
@Setter
@NoArgsConstructor
public class PortfolioVote {

    @Id

    @SequenceGenerator(
            name = "portfolio_vote_sequence",
            sequenceName = "portfolio_vote_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portfolio_vote_sequence"
    )
    private Long id;

    @ManyToOne
    private Investor investor;

    @ManyToOne
    private Portfolio portfolio;

    @Column
    private Enum<VoteEnum> voteDirection;
}
