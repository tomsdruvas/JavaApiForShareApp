package com.example.demo.shareDataDaily;


import com.example.demo.shareItem.ShareItem;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
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
    @Column(nullable = false, unique = true)
    private String symbol;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
//    @Type(type = "json")
//    @Column(columnDefinition = "json")
//    private String dailyData;
    @OneToOne(mappedBy = "shareDataDaily")
    private ShareItem shareItem;


    public ShareDataDaily(String symbol, LocalDateTime updatedAt, ShareItem shareItem) {
        this.symbol = symbol;
        this.updatedAt = updatedAt;
//        this.dailyData = dailyData;
        this.shareItem = shareItem;
    }
}
