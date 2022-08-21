package com.example.demo.portfolioVote;


import com.example.demo.comment.Comment;
import com.example.demo.investor.Investor;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.utils.VoteEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueInvestorAndPortfolio",
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

    @Column(name = "investor_id")
    private Long investorId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "investor_id", insertable = false, updatable = false)
    @NonNull
    private Investor investor;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
    @NonNull
    private Portfolio portfolio;

    @Column
    @NonNull
    private VoteEnum voteDirection;

    public PortfolioVote(Long investorId, Long portfolioId, @NonNull VoteEnum voteDirection) {
        this.investorId = investorId;
        this.portfolioId = portfolioId;
        this.voteDirection = voteDirection;
    }

    public PortfolioVote(Long id, Long investorId, Long portfolioId, @NonNull VoteEnum voteDirection) {
        this.id = id;
        this.investorId = investorId;
        this.portfolioId = portfolioId;
        this.voteDirection = voteDirection;
    }
}
