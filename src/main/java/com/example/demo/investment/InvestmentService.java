package com.example.demo.investment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }


    public List<Investment> getAll() {
        return investmentRepository.findAll();
    }



    public Investment getById(long investmentId) throws EntityNotFoundException {

        boolean exists = investmentRepository.existsById(investmentId);
        if (!exists) {
            throw new EntityNotFoundException("Investment with " + investmentId + " doesn't exist");
        }

        return investmentRepository.findInvestmentById(investmentId);
    }




    public Investment save(Investment newInvestment) {
        return investmentRepository.save(newInvestment);
    }




    public void removeInvestmentById(Long investmentId) throws EntityNotFoundException {
        boolean exists = investmentRepository.existsById(investmentId);
        if (!exists) {
            throw new EntityNotFoundException("Investment with " + investmentId + " doesn't exist");
        }
        investmentRepository.deleteById(investmentId);
    }


    public Investment updateById(long id, Investment updatedInvestmentDetails) {
        boolean exists = investmentRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("Investment with " + id + " doesn't exist");
        }
        Investment investment = investmentRepository.findInvestmentById(id);

        investment.setShareItemId(updatedInvestmentDetails.getShareItemId());
        investment.setQuantity(updatedInvestmentDetails.getQuantity());
        investment.setEntryPrice(updatedInvestmentDetails.getEntryPrice());
        investment.setPortfolioId(updatedInvestmentDetails.getPortfolioId());
        investment.setEntryDate(updatedInvestmentDetails.getEntryDate());


        return investmentRepository.save(investment);
    }

}
