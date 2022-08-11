package com.example.demo.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public Investor getById(long investorId) throws EntityNotFoundException {

        boolean exists = investorRepository.existsById(investorId);
        if (!exists) {
            throw new EntityNotFoundException("Investor with " + investorId + " doesn't exist");
        }

        return investorRepository.findInvestorById(investorId);
    }

    public Investor save(Investor newInvestor) {
        return investorRepository.save(newInvestor);
    }

    public void removeInvestorByID(Long investorId) throws EntityNotFoundException {
    boolean exists = investorRepository.existsById(investorId);
    if (!exists) {
        throw new EntityNotFoundException("Investor with " + investorId + " doesn't exist");
    }
    investorRepository.deleteById(investorId);
    }


    public Investor updateById(long id, Investor updatedInvestorDetails) {
        boolean exists = investorRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("Investor with " + id + " doesn't exist");
        }
        Investor investor = investorRepository.findInvestorById(id);

        investor.setName(updatedInvestorDetails.getName());
        investor.setEmail(updatedInvestorDetails.getEmail());

        return investorRepository.save(investor);
    }
}
