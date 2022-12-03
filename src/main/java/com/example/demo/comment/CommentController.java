package com.example.demo.comment;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/comment")
public class CommentController {

    private  final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }



    @GetMapping
    public ResponseEntity<List<Comment>> getAll(){
        return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<Comment>> getAllByPortfolioId(@PathVariable(value = "portfolioId") Long portfolioId){
        return new ResponseEntity<>(commentService.getByPortfolioId(portfolioId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable(value = "id") Long commentId){
        try {
            return commentService.getById(commentId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Comment Not Found", exc);
        }
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Comment newComment(@Valid @RequestBody Comment commentRequest){
        return commentService.save(commentRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody Comment updatedCommentDetails){
        Comment updatePortfolio = commentService.updateById(id, updatedCommentDetails);
        return ResponseEntity.ok(updatePortfolio);
    }

    @DeleteMapping("/{id}")
    public void  deleteComment(@PathVariable(value = "id") Long id){
        try {
            commentService.removeCommentByID(id);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Comment Not Found", exc);
        }
    }



}
