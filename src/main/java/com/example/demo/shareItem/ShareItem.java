package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDaily;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.web.SortDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "share_item")
@Getter
@Setter
@NoArgsConstructor
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
    private String name;
    @Column(nullable = false, unique = true)
    private String symbol;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany
    @OrderBy("date")
    @Column(nullable = false)
    private List<ShareDataDaily> shareDataDailies;


    public ShareItem(Long id, String name, String symbol, Double price, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public ShareItem(String name, String symbol, Double price, LocalDateTime updatedAt) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.updatedAt = updatedAt;
    }

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
