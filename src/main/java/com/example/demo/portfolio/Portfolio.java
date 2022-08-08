package com.example.demo.portfolio;

import com.example.demo.investment.Investment;
import com.example.demo.investor.Investor;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ManyToOne
    @NonNull
    private Investor investor;


    @Column
    @NonNull
    private Boolean isPublic;


}
