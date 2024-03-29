package com.TigranCorporations.oauth2.core.utility;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OauthParams {
    @Value("${clientId}")
    private String clientId;

    @Value("${base.url}")
    private String redirectURI;

    @Value("${scope}")
    private String scope;


    @Value("${secret}")
    private String secret;

    @Value("${oauth2.type}")
    private String type;

    @Value("${oauth2.endpoint}")
    private String oauth2Endpoint;

    @Value("${oauth2.token.endpoint}")
    private String tokenEndpoint;

    @Value("${oauth2.refresh.endpoint}")
    private String refreshEndpoint;

    @Value("${user.info.endpoint}")
    private String userInfoEndpoint;

    @Value("${test.refreshToken}")
    private String testRefreshToken;

}
