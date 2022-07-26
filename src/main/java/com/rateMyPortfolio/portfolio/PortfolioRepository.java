package com.rateMyPortfolio.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findPortfolioById(Long id);
    Portfolio findPortfolioByName(String name);
}
