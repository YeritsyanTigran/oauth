package com.TigranCorporations.oauth2.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
public class AccessToken implements Serializable {
    private String token;
    private long expiresIn;

    public AccessToken(String token,long expiresIn){
        this.token = token;
        this.expiresIn= System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn);
    }
}
