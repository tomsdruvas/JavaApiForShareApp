package com.example.demo.comment;

import com.example.demo.investor.Investor;
import com.example.demo.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ManyToOne
    @NonNull
    private Investor investor;

    @ManyToOne
    @NonNull
    private Portfolio portfolio;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date date;

    @Column
    @NonNull
    private String content;


}
