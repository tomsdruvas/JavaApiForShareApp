package com.example.demo.portfolioVote;

import com.example.demo.commentVote.CommentVote;
import com.example.demo.utils.VoteEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioVoteServiceTest {

    @Mock
    private PortfolioVoteRepository portfolioVoteRepository;

    private PortfolioVoteService underTest;



    @BeforeEach
    void setUp() {
        underTest = new PortfolioVoteService(portfolioVoteRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCommentVotes_success() {
        underTest.getAll();
        verify(portfolioVoteRepository).findAll();
    }

    @Test
    void saveUsingService_success() {
        PortfolioVote expected = new PortfolioVote(2L,3L, VoteEnum.valueOf("UP"));

        when(portfolioVoteRepository.save(expected))
                .thenReturn(expected);
        PortfolioVote actual = underTest.save(expected);
        verify(portfolioVoteRepository).save(expected);
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void updateCommentVoteById_success() {

        Long portfolioVoteId = 1L;
        PortfolioVote portfolioVoteForMock = new PortfolioVote(portfolioVoteId, 2L, 3L, VoteEnum.valueOf("UP"));

        PortfolioVote updatePortfolioVoteDetails = new PortfolioVote(2L, 3L, VoteEnum.valueOf("DOWN"));
        PortfolioVote updatedPortfolioVote = new PortfolioVote(portfolioVoteId, 2L, 3L, VoteEnum.valueOf("DOWN"));



        when(portfolioVoteRepository.existsById(1L)).thenReturn(true);
        doReturn(portfolioVoteForMock).when(portfolioVoteRepository).findPortfolioVoteById(portfolioVoteId);
        when(portfolioVoteRepository.save(portfolioVoteForMock)).thenReturn(updatedPortfolioVote);


        PortfolioVote actual = underTest.updateById(portfolioVoteId, updatePortfolioVoteDetails);
        Assertions.assertThat(actual).isEqualTo(updatedPortfolioVote);

        verify(portfolioVoteRepository).existsById(1L);
        verify(portfolioVoteRepository).findPortfolioVoteById(1L);
        verify(portfolioVoteRepository).save(portfolioVoteForMock);

    }

    @Test
    void removeCommentVoteByID_success() {

        when(portfolioVoteRepository.existsById(1L)).thenReturn(true);

        doNothing().when(portfolioVoteRepository).deleteById(1L);

        underTest.removePortfolioVoteById(1L);

        verify(portfolioVoteRepository).existsById(1L);
        verify(portfolioVoteRepository).deleteById(1L);
    }
}