package com.example.demo.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }


    public List<Investor> getAll() {
        return investorRepository.findAll();
    }

    public Optional<Investor> getById(long id) {
        return investorRepository.findById(id);
    }

    public Investor save(Investor newInvestor) {
        return investorRepository.save(newInvestor);
    }

    public void delete(long id) {
        investorRepository.deleteById(id);
    }


}
