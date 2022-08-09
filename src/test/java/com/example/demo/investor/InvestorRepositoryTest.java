package com.example.demo.investor;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestorRepositoryTest {

    @Autowired
    private InvestorRepository underTest;

    @Test
    void shouldBeAbleToCreateAnInvestor(){
        Investor investor = new Investor("Jack", "ir@financialshop.com");
        underTest.save(investor);
        List<Investor> listOfInvestors = underTest.findAll();

        assertEquals(listOfInvestors.size(),1);
    }

    @Test
    void shouldBeAbleToFindByName(){
        Investor investor = new Investor("Jack", "Jack@mail.com");
        underTest.save(investor);

        Investor investorEntity = underTest.findByName("Jack");
        assertThat(investorEntity.getEmail()).isEqualTo("Jack@mail.com");
    }



}