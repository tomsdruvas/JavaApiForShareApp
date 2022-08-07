package com.example.demo.portfolio;

import com.example.demo.investment.Investment;
import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
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
    private String name;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    @ManyToMany
    @JoinTable(
            name = "portfolio_holdings",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "investment_id"))
    private List<Investment> investments;


    @Column
    private Boolean isPublic;



}
