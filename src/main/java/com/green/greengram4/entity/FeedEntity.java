package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST)
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList();


}
