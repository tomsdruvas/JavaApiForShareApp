package com.rateMyPortfolio.portfolio;

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
import com.rateMyPortfolio.comment.Comment;
import com.rateMyPortfolio.investment.Investment;

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
public class Portfolio {

    @Id

    @SequenceGenerator(
            name = "portfolio_sequence",
            sequenceName = "portfolio_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portfolio_sequence"
    )
    private Long id;

    @Column
    @NonNull
    private String name;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date createdDate;

    @OneToMany
    @JoinColumn(name = "portfolio_id")
    private Set<Investment> investments;

    @Column(name = "investor_id")
    private Long investorId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "investor_id", insertable = false, updatable = false)
    @NonNull
    private ApplicationUser applicationUser;

    @Column
    @NonNull
    private Boolean isPublic;

    @OneToMany
    @JoinColumn(name = "portfolio_id")
    private Set<Comment> comments;


    public Portfolio(Long id, @NonNull String name, @NonNull Date createdDate, @NonNull ApplicationUser applicationUser, @NonNull Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.applicationUser = applicationUser;
        this.isPublic = isPublic;
    }

    public Portfolio(@NonNull String name, @NonNull Date createdDate, @NonNull Long investorId, @NonNull Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.investorId = investorId;
        this.isPublic = isPublic;
    }

}
