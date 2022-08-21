package com.example.demo.commentVote;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "api/commentvote")
public class CommentVoteController {

    private final CommentVoteService commentVoteService;

    @Autowired
    public CommentVoteController(CommentVoteService commentVoteService) {
        this.commentVoteService = commentVoteService;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentVote newCommentVote(@Valid @RequestBody CommentVote newCommentVote) {
        return commentVoteService.save(newCommentVote);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CommentVote> updateCommentVote(@PathVariable long id, @RequestBody CommentVote updatedCommentVote){
        CommentVote updateCommentVote = commentVoteService.updateById(id, updatedCommentVote);

        return ResponseEntity.ok(updateCommentVote);

    }

    @DeleteMapping("/{id}")
    public void  deleteCommentVote(@PathVariable(value = "id") Long id){
        try {
            commentVoteService.removeCommentVoteById(id);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "CommentVote Not Found", exc);
        }
    }
}
