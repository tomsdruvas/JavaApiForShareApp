package com.example.demo.portfolio;

import com.example.demo.investment.Investment;
import com.example.demo.investor.Investor;
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
    private Investor investor;


    @Column
    @NonNull
    private Boolean isPublic;


    public Portfolio(Long id, @NonNull String name, @NonNull Date createdDate, @NonNull Investor investor, @NonNull Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.investor = investor;
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
