package com.rateMyPortfolio.comment;

import com.rateMyPortfolio.commentVote.CommentVote;
import com.rateMyPortfolio.investor.Investor;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
    private Investor investor;

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
