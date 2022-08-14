package com.example.demo.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

//    @Query(value = "SELECT * FROM comment WHERE portfolio_id = :pi", nativeQuery = true)
//    List<Comment>  findByPortfolioId(@Param("pi")String portfolioId);

    Comment findCommentById(long commentId);
    List<Comment> findCommentsByPortfolioId(Long id);
}
