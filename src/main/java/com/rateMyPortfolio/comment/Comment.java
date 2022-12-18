package com.rateMyPortfolio.comment;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.commentVote.CommentVote;
import com.rateMyPortfolio.portfolio.Portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id

    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long id;

    @Column(name = "investor_id")
    private Long investorId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "investor_id", insertable = false, updatable = false)
    @NonNull
    private ApplicationUser applicationUser;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
    @NonNull
    private Portfolio portfolio;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date date;

    @Column
    @NonNull
    private String content;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private Set<CommentVote> commentVotes;


    public Comment(Long id, Long investorId, Long portfolioId, @NonNull Date date, @NonNull String content) {
        this.id = id;
        this.investorId = investorId;
        this.portfolioId = portfolioId;
        this.date = date;
        this.content = content;
    }

    public Comment(Long investorId, Long portfolioId, @NonNull Date date, @NonNull String content) {
        this.investorId = investorId;
        this.portfolioId = portfolioId;
        this.date = date;
        this.content = content;
    }
}
