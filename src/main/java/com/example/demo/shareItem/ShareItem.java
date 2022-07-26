package com.example.demo.shareItem;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
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
    private Integer price;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public ShareItem() {
    }

    public ShareItem(Long id, String name, String symbol, Integer price, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public ShareItem(String name, String symbol, Integer price, LocalDateTime updatedAt) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
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
