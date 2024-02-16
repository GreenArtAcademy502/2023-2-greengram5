package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_feed_pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPicsEntity extends CreatedAtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long ifeedPics;

    @ManyToOne
    @JoinColumn(name = "ifeed")
    private FeedEntity feedEntity;

    @Column(length = 2500, nullable = false)
    private String pic;
}
