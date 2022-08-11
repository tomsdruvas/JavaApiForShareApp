package com.example.demo.portfolio;

import com.example.demo.investor.Investor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
    void save() {
    }

    @Test
    void removePortfolioByID() {
    }

    @Test
    void updateById() {
    }
}