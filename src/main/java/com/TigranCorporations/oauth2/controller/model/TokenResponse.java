package com.TigranCorporations.oauth2.controller.model;

import com.TigranCorporations.oauth2.core.deserializers.TokenResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = TokenResponseDeserializer.class)
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
