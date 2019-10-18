package com.TigranCorporations.oauth2.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AbstractEntity {
    private String name;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "account_id")
    private String accountId;

    private String type;
}
