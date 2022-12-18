package com.rateMyPortfolio.applicationUser;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ApplicationUserRepositoryTest {

    @Autowired
    private InvestorRepository underTest;

    @Test
    void shouldBeAbleToCreateAnInvestor(){
        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        underTest.save(applicationUser);
        List<ApplicationUser> listOfApplicationUsers = underTest.findAll();

        assertEquals(listOfApplicationUsers.size(),1);
    }

    @Test
    void shouldBeAbleToFindByName(){
        ApplicationUser applicationUser = new ApplicationUser("Jack", "Jack@mail.com");
        underTest.save(applicationUser);

        ApplicationUser applicationUserEntity = underTest.findByName("Jack");
        assertThat(applicationUserEntity.getEmail()).isEqualTo("Jack@mail.com");
    }

    @Test
    void shouldBeAbleToUpdateInvestorById(){
        ApplicationUser applicationUser = new ApplicationUser("Jack", "Jack@mail.com");
        ApplicationUser savedApplicationUser = underTest.save(applicationUser);

        ApplicationUser updatedDetails = new ApplicationUser("John", "John@mail.com");

        savedApplicationUser.setName(updatedDetails.getName());
        savedApplicationUser.setEmail(updatedDetails.getEmail());

        ApplicationUser updatedApplicationUser = underTest.save(applicationUser);

        assertThat(updatedApplicationUser.getName()).isEqualTo("John");
        assertThat(updatedApplicationUser.getEmail()).isEqualTo("John@mail.com");

    }



}