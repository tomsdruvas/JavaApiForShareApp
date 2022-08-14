package com.example.demo.comment;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService underTest;



    @BeforeEach
    void setUp() {
        underTest = new CommentService(commentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldBeAbleToGetAllComments() {
        underTest.getAll();
        verify(commentRepository).findAll();
    }

    @Test
    void shouldBeAbleToGetCommentById() {
        Long commentId = 1L;
        Long portfolioId = 2L;
        Long investorId = 3L;

        Comment commentForMock = new Comment(commentId, portfolioId, investorId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");

        doReturn(commentForMock).when(commentRepository).findCommentById(commentId);
        doReturn(true).when(commentRepository).existsById(commentId);

        Comment commentEntityFromService = underTest.getById(commentId);

        assertThat(commentEntityFromService.getContent()).isEqualTo(commentForMock.getContent());
        verify(commentRepository).existsById(commentId);
        verify(commentRepository).findCommentById(commentId);


    }

    @Test
    void shouldBeAbleToGetCommentByPortfolioId_success() {
        Long commentId = 1L;
        Long portfolioId = 2L;
        Long investorId = 3L;

        Comment commentForMock = new Comment(commentId, portfolioId, investorId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");
        Comment commentForMock2 = new Comment(commentId, portfolioId, investorId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");
        Comment commentForMock3 = new Comment(commentId, portfolioId, investorId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");

        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentForMock);
        commentList.add(commentForMock2);
        commentList.add(commentForMock3);



        doReturn(commentList).when(commentRepository).findCommentsByPortfolioId(portfolioId);

        List<Comment> commentEntityFromService = underTest.getByPortfolioId(portfolioId);

        assertThat(commentEntityFromService.size()).isEqualTo(3);
        verify(commentRepository).findCommentsByPortfolioId(portfolioId);


    }


    @Test
    void shouldBeAbleToSave_success() {
        Long commentId = 1L;
        Long portfolioId = 2L;
        Long investorId = 3L;

        Comment commentForMock = new Comment(commentId, portfolioId, investorId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");


        when(commentRepository.save(commentForMock))
                .thenReturn(commentForMock);
        Comment actual = underTest.save(commentForMock);
        verify(commentRepository).save(commentForMock);
        assertThat(commentForMock).isEqualTo(actual);
    }

    @Test
    void shouldBeAbleToRemoveCommentByID_success() {
        when(commentRepository.existsById(1L)).thenReturn(true);

        doNothing().when(commentRepository).deleteById(1L);

        underTest.removeCommentByID(1L);

        verify(commentRepository).existsById(1L);
        verify(commentRepository).deleteById(1L);

    }

    @Test
    void shouldBeAbleToUpdateById_success() {

        Long commentId = 1L;

        Comment commentForMock = new Comment(commentId, 1L, 1L, Date.valueOf("2022-02-20"), "This portfolio has so much potential");

        Comment updateCommentDetails = new Comment(1L, 1L, Date.valueOf("2022-02-20"), "This portfolio has so much potential, it might grow!");
        Comment updatedComment = new Comment(commentId, 1L, 1L, Date.valueOf("2022-02-20"), "This portfolio has so much potential, it might grow!");


        when(commentRepository.existsById(1L)).thenReturn(true);
        doReturn(commentForMock).when(commentRepository).findCommentById(commentId);
        when(commentRepository.save(commentForMock)).thenReturn(updatedComment);


        Comment actual = underTest.updateById(commentId, updateCommentDetails);

        Assertions.assertThat(actual).isEqualTo(updatedComment);
        verify(commentRepository).existsById(1L);
        verify(commentRepository).findCommentById(1L);
        verify(commentRepository).save(commentForMock);
    }
}