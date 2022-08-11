package com.example.demo.investor;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestorServiceTest {

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
    void getById() {
        Long investorId = 1L;
        Investor investorForMock = new Investor(investorId, "John","John@mail.com");
        doReturn(Optional.of(investorForMock)).when(investorRepository).findById(investorId);

        Investor investorEntityFromService = underTest.getById(investorId);

        assertThat(investorId).isEqualTo(investorEntityFromService.getId());
        assertThat(investorForMock.getName()).isEqualTo(investorEntityFromService.getName());
        verify(investorRepository).findById(investorId);
    }

    @Test
    void canSaveUsingService() {
        Investor expected = new Investor("Jack", "Jack@financials.com");
        when(investorRepository.save(expected))
                .thenReturn(expected);
        Investor actual = underTest.save(expected);
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
        assertTrue(thrown.getMessage().contains("Investor with" + 1L + "doesn't exist"));

    }

    @Test
    void updateInvestorById_success() throws Exception {
        Long investorId = 1L;
        Investor investorForMock = new Investor(investorId, "John","John@mail.com");
        Investor updateInvestor = new Investor("Updated","Jack@mail.com");
        Investor updatedInvestor = new Investor(investorId,"Updated","Jack@mail.com");


        when(investorRepository.existsById(1L)).thenReturn(true);
        doReturn(Optional.of(investorForMock)).when(investorRepository).findById(investorId);
        when(investorRepository.save(investorForMock)).thenReturn(updatedInvestor);


        Investor actual = underTest.updateById(investorId, updateInvestor);
        assertThat(actual).isEqualTo(updatedInvestor);

        verify(investorRepository).existsById(1L);
        verify(investorRepository).findById(1L);
        verify(investorRepository).save(investorForMock);





    }

}