package com.example.demo.investor;

import com.example.demo.comment.Comment;
import com.example.demo.portfolio.Portfolio;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Investor {
    @Id

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

    @Column(unique = true)
    @NonNull
    private String email;

    @OneToMany
    @JoinColumn(name = "investor_id")
    private Set<Portfolio> portfolios;

    @OneToMany
    @JoinColumn(name = "investor_id")
    private Set<Comment> comments;


}
