package com.example.demo.investor;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Investor findByName(String name);
    Investor findInvestorById(Long id);

}
