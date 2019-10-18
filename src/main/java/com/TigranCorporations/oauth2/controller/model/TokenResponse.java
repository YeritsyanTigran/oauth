package com.TigranCorporations.oauth2.controller.model;

import com.TigranCorporations.oauth2.core.deserializers.GoogleTokenResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = GoogleTokenResponseDeserializer.class)
public class TokenResponse {
    private String accessToken;
    private String tokenId;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private String tokenType;
}
