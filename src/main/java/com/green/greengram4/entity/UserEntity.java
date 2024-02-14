package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iuser;

    @Column(length = 10, name = "provider_type", nullable = false)
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

}
