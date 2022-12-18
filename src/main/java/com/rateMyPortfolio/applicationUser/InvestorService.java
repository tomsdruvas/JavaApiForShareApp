package com.rateMyPortfolio.applicationUser;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }


    public List<ApplicationUser> getAll() {
        return investorRepository.findAll();
    }

    public ApplicationUser getById(long investorId) throws EntityNotFoundException {

        boolean exists = investorRepository.existsById(investorId);
        if (!exists) {
            throw new EntityNotFoundException("Investor with " + investorId + " doesn't exist");
        }

        return investorRepository.findInvestorById(investorId);
    }

    public ApplicationUser save(ApplicationUser newApplicationUser) {
        return investorRepository.save(newApplicationUser);
    }

    public void removeInvestorByID(Long investorId) throws EntityNotFoundException {
    boolean exists = investorRepository.existsById(investorId);
    if (!exists) {
        throw new EntityNotFoundException("Investor with " + investorId + " doesn't exist");
    }
    investorRepository.deleteById(investorId);
    }


    public ApplicationUser updateById(long id, ApplicationUser updatedApplicationUserDetails) {
        boolean exists = investorRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("Investor with " + id + " doesn't exist");
        }
        ApplicationUser applicationUser = investorRepository.findInvestorById(id);

        applicationUser.setName(updatedApplicationUserDetails.getName());
        applicationUser.setEmail(updatedApplicationUserDetails.getEmail());

        return investorRepository.save(applicationUser);
    }
}
