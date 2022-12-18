package com.rateMyPortfolio.applicationUser;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    private InvestorService underTest;

    @BeforeEach
    void setUp() {
        underTest = new InvestorService(investorRepository);




    }

    @Test
    void shouldBeAbleToGetAllInvestors() {
        underTest.getAll();
        verify(investorRepository).findAll();
    }

    @Test
    void canGetInvestorByIdFromService() {
        Long investorId = 1L;
        ApplicationUser applicationUserForMock = new ApplicationUser(investorId, "John","John@mail.com");
        doReturn(applicationUserForMock).when(investorRepository).findInvestorById(investorId);
        doReturn(true).when(investorRepository).existsById(investorId);

        ApplicationUser applicationUserEntityFromService = underTest.getById(investorId);

        assertThat(investorId).isEqualTo(applicationUserEntityFromService.getId());
        assertThat(applicationUserForMock.getName()).isEqualTo(applicationUserEntityFromService.getName());
        verify(investorRepository).findInvestorById(investorId);
    }

    @Test
    void canSaveUsingService() {
        ApplicationUser expected = new ApplicationUser("Jack", "Jack@financials.com");
        when(investorRepository.save(expected))
                .thenReturn(expected);
        ApplicationUser actual = underTest.save(expected);
        verify(investorRepository).save(expected);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldDeleteWhenValidInvestorId() {
        when(investorRepository.existsById(1L)).thenReturn(true);

        doNothing().when(investorRepository).deleteById(1L);

        underTest.removeInvestorByID(1L);

        verify(investorRepository).existsById(1L);
        verify(investorRepository).deleteById(1L);

    }

    @Test
    void removeInvestorByIdThatDoesNotExist(){
        when(investorRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> underTest.removeInvestorByID(1L),
                "Expected underTest.removeInvestorByID to throw, but it didn't");

        assertNotNull(thrown);
        assertTrue(thrown.getMessage().contains("Investor with " + 1L + " doesn't exist"));

    }

    @Test
    void updateInvestorById() {
        Long investorId = 1L;
        ApplicationUser applicationUserForMock = new ApplicationUser(investorId, "John","John@mail.com");
        ApplicationUser updateApplicationUser = new ApplicationUser("Updated","Jack@mail.com");
        ApplicationUser updatedApplicationUser = new ApplicationUser(investorId,"Updated","Jack@mail.com");


        when(investorRepository.existsById(1L)).thenReturn(true);
        doReturn(applicationUserForMock).when(investorRepository).findInvestorById(investorId);
        when(investorRepository.save(applicationUserForMock)).thenReturn(updatedApplicationUser);


        ApplicationUser actual = underTest.updateById(investorId, updateApplicationUser);
        assertThat(actual).isEqualTo(updatedApplicationUser);

        verify(investorRepository).existsById(1L);
        verify(investorRepository).findInvestorById(1L);
        verify(investorRepository).save(applicationUserForMock);





    }

}