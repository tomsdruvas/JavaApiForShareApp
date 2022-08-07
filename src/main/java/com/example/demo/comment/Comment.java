package com.example.demo.comment;

import com.example.demo.investor.Investor;
import com.example.demo.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne
    private Investor investor;

    @ManyToOne
    private Portfolio portfolio;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @Column
    private String content;


}
