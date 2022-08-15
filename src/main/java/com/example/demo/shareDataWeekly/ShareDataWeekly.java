package com.example.demo.shareDataWeekly;


import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueDateAndSymbol",
        columnNames = { "date", "symbol" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareDataWeekly {

    @Id
    @SequenceGenerator(
            name = "sharedataweekly_sequence",
            sequenceName = "sharedataweekly_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sharedataweekly_sequence"
    )
    @JsonIgnore
    private Long id;
    @Column(nullable = false)
    @JsonIgnore
    @NonNull
    private String symbol;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date date;
    @Column
    @NonNull
    private Double openPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_item_id")
    @JsonIgnore
    @NonNull
    private ShareItem shareItem;

    public ShareDataWeekly(String symbol, Date date, Double openPrice, ShareItem shareItem) {
        this.symbol = symbol;
        this.date = date;
        this.openPrice = openPrice;
        this.shareItem = shareItem;
    }

}