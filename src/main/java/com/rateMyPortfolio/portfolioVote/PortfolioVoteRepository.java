package com.rateMyPortfolio.portfolioVote;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioVoteRepository extends JpaRepository<PortfolioVote, Long> {

    PortfolioVote findPortfolioVoteById(Long id);
}
