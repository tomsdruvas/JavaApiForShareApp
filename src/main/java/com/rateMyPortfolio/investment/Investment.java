package com.rateMyPortfolio.investment;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.shareItem.ShareItem;

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
    @JoinColumn(name = "share_item_id", insertable = false, updatable = false)
    @JsonIgnore
    @NonNull
    private ShareItem shareItem;

    @Column(name = "share_item_id")
    private Long shareItemId;

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
    @JsonIgnore
    @NonNull
    @JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
    private Portfolio portfolio;

    @Column(name = "portfolio_id")
    private Long portfolioId;


    public Investment(Long shareItemId, @NonNull Double quantity, @NonNull Double entryPrice, @NonNull Date entryDate, Long portfolioId) {
        this.shareItemId = shareItemId;
        this.quantity = quantity;
        this.entryPrice = entryPrice;
        this.entryDate = entryDate;
        this.portfolioId = portfolioId;
    }

    public Investment(Long id, Long shareItemId, @NonNull Double quantity, @NonNull Double entryPrice, @NonNull Date entryDate, Long portfolioId) {
        this.id = id;
        this.shareItemId = shareItemId;
        this.quantity = quantity;
        this.entryPrice = entryPrice;
        this.entryDate = entryDate;
        this.portfolioId = portfolioId;
    }
}
