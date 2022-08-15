package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;



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
    @Column(nullable = false)
    @NonNull
    private Double price;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NonNull
    private Date updatedAt;

    @OneToMany(mappedBy = "shareItem", fetch = FetchType.LAZY)
    @OrderBy("date")
    private List<ShareDataDaily> shareDataDailies;

    @OneToMany(mappedBy = "shareItem", fetch = FetchType.LAZY)
    @OrderBy("date")
    private List<ShareDataWeekly> shareDataWeeklies;



    @Override
    public String toString() {
        return "ShareItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
