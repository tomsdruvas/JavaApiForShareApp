package com.example.demo.comment;

import com.example.demo.investor.Investor;
import com.example.demo.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
