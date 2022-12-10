package com.rateMyPortfolio.portfolio;

import com.rateMyPortfolio.investor.Investor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    private PortfolioService underTest;

    private Investor investor;

    @BeforeEach
    void setUp() {
        underTest = new PortfolioService(portfolioRepository);
        investor = new Investor(1L, "Jack", "ir@financialshop.com");
    }

    @AfterEach
    void tearDown() {
        portfolioRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToGetAllPortfolios() {
        underTest.getAll();
        verify(portfolioRepository).findAll();
    }

    @Test
    void shouldBeAbleToGetPortfolioById() {
        Long portfolioId = 1L;
        Portfolio portfolioForMock = new Portfolio(portfolioId, "Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);

        doReturn(portfolioForMock).when(portfolioRepository).findPortfolioById(portfolioId);
        doReturn(true).when(portfolioRepository).existsById(portfolioId);

        Portfolio portfolioEntityFromService = underTest.getById(portfolioId);

        assertThat(portfolioId).isEqualTo(portfolioEntityFromService.getId());
        assertThat(portfolioForMock.getName()).isEqualTo(portfolioEntityFromService.getName());
        verify(portfolioRepository).findPortfolioById(portfolioId);
    }

    @Test
    void shouldBeAbleToSaveViaService() {

        Portfolio expected = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);

        when(portfolioRepository.save(expected))
                    .thenReturn(expected);
        Portfolio actual = underTest.save(expected);
        verify(portfolioRepository).save(expected);
        assertThat(expected).isEqualTo(actual);

    }

    @Test
    void shouldDeleteWhenValidPortfolioId() {
        when(portfolioRepository.existsById(1L)).thenReturn(true);

        doNothing().when(portfolioRepository).deleteById(1L);

        underTest.removePortfolioByID(1L);

        verify(portfolioRepository).existsById(1L);
        verify(portfolioRepository).deleteById(1L);

    }

    @Test
    void updateInvestorById() {
        Long portfolioId = 1L;
        Portfolio portfolioForMock = new Portfolio(portfolioId,"Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);

        Portfolio updatePortfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);
        Portfolio updatedPortfolio = new Portfolio(portfolioId,"Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);


        when(portfolioRepository.existsById(1L)).thenReturn(true);
        doReturn(portfolioForMock).when(portfolioRepository).findPortfolioById(portfolioId);
        when(portfolioRepository.save(portfolioForMock)).thenReturn(updatedPortfolio);


        Portfolio actual = underTest.updateById(portfolioId, updatePortfolio);
        assertThat(actual).isEqualTo(updatedPortfolio);

        verify(portfolioRepository).existsById(1L);
        verify(portfolioRepository).findPortfolioById(1L);
        verify(portfolioRepository).save(portfolioForMock);
    }
}