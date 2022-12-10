package com.rateMyPortfolio.investor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Investor findByName(String name);
    Investor findInvestorById(Long id);

}
