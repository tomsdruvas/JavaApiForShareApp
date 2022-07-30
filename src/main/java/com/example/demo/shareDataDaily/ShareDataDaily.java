package com.example.demo.shareDataDaily;


import com.example.demo.shareItem.ShareItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
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
    @Column(columnDefinition = "JSON")
    private String dailyData;
    @OneToOne(mappedBy = "shareDataDaily")
    private ShareItem shareItem;


}
