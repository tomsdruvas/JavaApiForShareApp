package com.rateMyPortfolio.applicationUser;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rateMyPortfolio.comment.Comment;
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
public class ApplicationUser {

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

    public ApplicationUser(Long id, @NonNull String name, @NonNull String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
