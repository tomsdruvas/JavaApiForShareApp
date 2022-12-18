package com.rateMyPortfolio.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

//    @Query(value = "SELECT * FROM comment WHERE portfolio_id = :pi", nativeQuery = true)
//    List<Comment>  findByPortfolioId(@Param("pi")String portfolioId);

    Comment findCommentById(long commentId);
    List<Comment> findCommentsByPortfolioId(Long id);
}
