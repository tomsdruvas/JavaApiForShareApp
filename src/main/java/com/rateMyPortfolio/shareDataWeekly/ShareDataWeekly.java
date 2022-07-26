package com.rateMyPortfolio.shareDataWeekly;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rateMyPortfolio.shareItem.ShareItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


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

    @Column(nullable = false, columnDefinition="Decimal(10,3) default '100.000'")
    @NonNull
    private BigDecimal openPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_item_id")
    @JsonIgnore
    @NonNull
    private ShareItem shareItem;

    public ShareDataWeekly(String symbol, Date date, BigDecimal openPrice, ShareItem shareItem) {
        this.symbol = symbol;
        this.date = date;
        this.openPrice = openPrice;
        this.shareItem = shareItem;
    }

}