package com.rateMyPortfolio.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    public List<Comment> getByPortfolioId(Long portfolioId){
        return commentRepository.findCommentsByPortfolioId(portfolioId);
    }

    public Comment getById(long commentId) throws EntityNotFoundException {

        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new EntityNotFoundException("Comment with " + commentId + " ID doesn't exist");
        }

        return commentRepository.findCommentById(commentId);
    }

    public Comment save(Comment newComment) {
        return commentRepository.save(newComment);
    }

    public void removeCommentByID(Long commentId) throws EntityNotFoundException {
        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new EntityNotFoundException("Comment with " + commentId + " ID doesn't exist");
        }
        commentRepository.deleteById(commentId);
    }


    public Comment updateById(long commentId, Comment updatedCommentDetails) {
        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new EntityNotFoundException("Comment with " + commentId + " ID doesn't exist");
        }
        Comment comment = commentRepository.findCommentById(commentId);

        comment.setContent(updatedCommentDetails.getContent());

        return commentRepository.save(comment);
    }



}
