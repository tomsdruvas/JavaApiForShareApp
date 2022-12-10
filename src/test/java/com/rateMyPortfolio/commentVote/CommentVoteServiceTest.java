package com.rateMyPortfolio.commentVote;

import com.rateMyPortfolio.utils.VoteEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentVoteServiceTest {

    @Mock
    private CommentVoteRepository commentVoteRepository;

    private CommentVoteService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CommentVoteService(commentVoteRepository);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllCommentVotes_success() {
        underTest.getAll();
        verify(commentVoteRepository).findAll();
    }

    @Test
    void saveUsingService_success() {
        CommentVote expected = new CommentVote(2L,3L, VoteEnum.valueOf("UP"));

        when(commentVoteRepository.save(expected))
                .thenReturn(expected);
        CommentVote actual = underTest.save(expected);
        verify(commentVoteRepository).save(expected);
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void updateCommentVoteById_success() {

        Long commentVoteId = 1L;
        CommentVote commentVoteForMock = new CommentVote(commentVoteId, 2L, 3L, VoteEnum.valueOf("UP"));

        CommentVote updateCommentVoteDetails = new CommentVote(2L, 3L, VoteEnum.valueOf("DOWN"));
        CommentVote updatedCommentVote = new CommentVote(commentVoteId, 2L, 3L, VoteEnum.valueOf("DOWN"));



        when(commentVoteRepository.existsById(1L)).thenReturn(true);
        doReturn(commentVoteForMock).when(commentVoteRepository).findCommentVoteById(commentVoteId);
        when(commentVoteRepository.save(commentVoteForMock)).thenReturn(updatedCommentVote);


        CommentVote actual = underTest.updateById(commentVoteId, updateCommentVoteDetails);
        Assertions.assertThat(actual).isEqualTo(updatedCommentVote);

        verify(commentVoteRepository).existsById(1L);
        verify(commentVoteRepository).findCommentVoteById(1L);
        verify(commentVoteRepository).save(commentVoteForMock);

    }

    @Test
    void removeCommentVoteByID_success() {

        when(commentVoteRepository.existsById(1L)).thenReturn(true);

        doNothing().when(commentVoteRepository).deleteById(1L);

        underTest.removeCommentVoteById(1L);

        verify(commentVoteRepository).existsById(1L);
        verify(commentVoteRepository).deleteById(1L);
    }
}