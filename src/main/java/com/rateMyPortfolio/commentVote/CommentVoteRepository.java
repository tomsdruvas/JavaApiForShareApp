package com.rateMyPortfolio.commentVote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    CommentVote findCommentVoteById(Long id);
}
