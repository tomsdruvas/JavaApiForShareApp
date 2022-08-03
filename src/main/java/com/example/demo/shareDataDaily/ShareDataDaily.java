package com.example.demo.shareDataDaily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShareDataDaily {

    @Id
    @SequenceGenerator(
            name = "sharedatadaily_sequence",
            sequenceName = "sharedatadaily_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sharedatadaily_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String symbol;
    @Column(nullable = false)
    private String date;
    @Column
    private Double openPrice;

    public ShareDataDaily(String symbol, String date, Double openPrice) {
        this.symbol = symbol;
        this.date = date;
        this.openPrice = openPrice;
    }
}
