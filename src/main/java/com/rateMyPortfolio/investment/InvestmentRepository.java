package com.rateMyPortfolio.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Investment findInvestmentById(Long id);
}
