package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @JsonIgnore
    private Long id;
    @Column(nullable = false)
    private String symbol;
    @Column(nullable = false)
    private String date;
    @Column
    private Double openPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ShareItem shareItem;

    public ShareDataDaily(String symbol, String date, Double openPrice, ShareItem shareItem) {
        this.symbol = symbol;
        this.date = date;
        this.openPrice = openPrice;
        this.shareItem = shareItem;
    }

}
