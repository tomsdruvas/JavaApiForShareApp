package com.rateMyPortfolio.portfolioVote;


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
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.utils.VoteEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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
    private ApplicationUser applicationUser;

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
