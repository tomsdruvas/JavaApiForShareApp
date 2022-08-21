package com.example.demo.commentVote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;

    @Autowired
    public CommentVoteService(CommentVoteRepository commentVoteRepository) {
        this.commentVoteRepository = commentVoteRepository;
    }

    public List<CommentVote> getAll() {
        return commentVoteRepository.findAll();
    }

    public CommentVote save(CommentVote newCommentVote) {
        return commentVoteRepository.save(newCommentVote);
    }

    public CommentVote updateById(long id, CommentVote updatedCommentVote) {
        boolean exists = commentVoteRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("CommentVote with " + id + " doesn't exist");
        }
        CommentVote commentVote = commentVoteRepository.findCommentVoteById(id);

        commentVote.setVoteDirection(updatedCommentVote.getVoteDirection());
        return commentVoteRepository.save(commentVote);
    }

    public void removeCommentVoteById(Long commentVoteId) throws EntityNotFoundException {
        boolean exists = commentVoteRepository.existsById(commentVoteId);
        if (!exists) {
            throw new EntityNotFoundException("CommentVote with" + commentVoteId + "doesn't exist");
        }
        commentVoteRepository.deleteById(commentVoteId);
    }
}
