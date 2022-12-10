package com.rateMyPortfolio.investment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceTest {

    @Mock
    private InvestmentRepository investmentRepository;

    private InvestmentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new InvestmentService(investmentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldBeAbleToGetAllInvestments() {
        underTest.getAll();
        verify(investmentRepository).findAll();
    }

    @Test
    void shouldBeAbleToGetInvestmentById() {
        Long investmentId = 1L;
        Investment investmentForMock = new Investment(investmentId, 1L, 30.00, 104.32, Date.valueOf("2022-08-11"), 1L);

        doReturn(investmentForMock).when(investmentRepository).findInvestmentById(investmentId);
        doReturn(true).when(investmentRepository).existsById(investmentId);

        Investment investmentEntityFromService = underTest.getById(investmentId);

        assertThat(investmentForMock.getId()).isEqualTo(investmentEntityFromService.getId());
        assertThat(investmentEntityFromService.getQuantity()).isEqualTo(investmentForMock.getQuantity());
        verify(investmentRepository).existsById(investmentId);
    }

    @Test
    void shouldBeAbleToSaveViaService() {

        Long investmentId = 1L;
        Investment expected = new Investment(investmentId, 1L, 30.00, 104.32, Date.valueOf("2022-08-11"), 1L);

        when(investmentRepository.save(expected))
                .thenReturn(expected);
        Investment actual = underTest.save(expected);
        verify(investmentRepository).save(expected);
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldBeAbleToRemoveInvestmentById() {
        when(investmentRepository.existsById(1L)).thenReturn(true);

        doNothing().when(investmentRepository).deleteById(1L);

        underTest.removeInvestmentById(1L);

        verify(investmentRepository).existsById(1L);
        verify(investmentRepository).deleteById(1L);

    }

    @Test
    void updateById() {
        Long investmentId = 1L;
        Investment investmentForMock = new Investment(investmentId, 1L, 30.00, 104.32, Date.valueOf("2022-08-11"), 1L);

        Investment updateInvestmentDetails = new Investment(1L, 15.00, 104.32, Date.valueOf("2022-08-11"), 1L);
        Investment updatedInvestment = new Investment(investmentId, 1L, 15.00, 104.32, Date.valueOf("2022-08-11"), 1L);



        when(investmentRepository.existsById(1L)).thenReturn(true);
        doReturn(investmentForMock).when(investmentRepository).findInvestmentById(investmentId);
        when(investmentRepository.save(investmentForMock)).thenReturn(updatedInvestment);


        Investment actual = underTest.updateById(investmentId, updateInvestmentDetails);
        Assertions.assertThat(actual).isEqualTo(updatedInvestment);

        verify(investmentRepository).existsById(1L);
        verify(investmentRepository).findInvestmentById(1L);
        verify(investmentRepository).save(investmentForMock);

    }
}