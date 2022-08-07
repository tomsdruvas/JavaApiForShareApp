package com.example.demo.investment;

import com.example.demo.portfolio.Portfolio;
import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Investment {

    @Id

    @SequenceGenerator(
            name = "investment_sequence",
            sequenceName = "investment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "investment_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NonNull
    private ShareItem shareItem;

    @Column
    @NonNull
    private Double quantity;

    @Column
    @NonNull
    private Double entryPrice;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date entryDate;

    @ManyToOne
    @NonNull
    private Portfolio portfolio;

}
