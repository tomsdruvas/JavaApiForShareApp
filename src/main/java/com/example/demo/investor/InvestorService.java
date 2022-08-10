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

    public Investor getById(long id) {
        return investorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Investor save(Investor newInvestor) {
        return investorRepository.save(newInvestor);
    }

    public void removeInvestorByID(Long InvestorId) throws EntityNotFoundException {
    boolean exists = investorRepository.existsById(InvestorId);
    if (!exists) {
        throw new EntityNotFoundException("Investor with" + InvestorId + "doesn't exist");
    }
    investorRepository.deleteById(InvestorId);
}


}
