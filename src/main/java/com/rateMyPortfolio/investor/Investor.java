package com.rateMyPortfolio.investor;

import com.rateMyPortfolio.comment.Comment;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Portfolio> portfolios;

    @OneToMany
    @JoinColumn(name = "investor_id")
    @JsonIgnore
    private Set<Comment> comments;

    public Investor(Long id, @NonNull String name, @NonNull String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}