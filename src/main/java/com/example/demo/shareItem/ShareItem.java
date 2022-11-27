package com.example.demo.shareItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.example.demo.utils.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "share_item")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ShareItem {
    @Id

    @SequenceGenerator(
            name = "shareitem_sequence",
            sequenceName = "shareitem_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shareitem_sequence"
    )
    private Long id;
    @Column(nullable = false, unique = true)
    @NonNull
    private String name;
    @Column(nullable = false, unique = true)
    @NonNull
    private String symbol;
    @Column(nullable = false, columnDefinition="Decimal(10,3) default '100.000'")
    @NonNull
    private BigDecimal price;

    @Column(nullable = false)
    @NonNull
    private CurrencyEnum currency;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date updatedAt;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NonNull
    private LocalDateTime addedAt;

    @JsonIgnore
    @Column(nullable = false)
    @NonNull
    private Boolean outstandingTask;

    @OneToMany(mappedBy = "shareItem", fetch = FetchType.LAZY)
    @OrderBy("date")
    private List<ShareDataDaily> shareDataDailies;

    @OneToMany(mappedBy = "shareItem", fetch = FetchType.LAZY)
    @OrderBy("date")
    private List<ShareDataWeekly> shareDataWeeklies;


}
