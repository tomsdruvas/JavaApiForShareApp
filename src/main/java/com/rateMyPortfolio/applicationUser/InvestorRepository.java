package com.rateMyPortfolio.applicationUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByName(String name);
    ApplicationUser findInvestorById(Long id);

}
