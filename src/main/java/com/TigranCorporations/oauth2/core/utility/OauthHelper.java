package com.TigranCorporations.oauth2.core.utility;

import com.TigranCorporations.oauth2.controller.model.AccessToken;
import com.TigranCorporations.oauth2.controller.model.TokenResponse;
import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.service.UserService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
public class OauthHelper {
    public static final String ACCESS_TOKEN_CACHE = "ACCESS_TOKEN";

    @Autowired
    private OauthParams oauthParams;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserService userService;

    public UserDto getCredentials(String code) throws IOException, TokenException {
        Map<String,String> params = new HashMap<>();
        params.put("client_secret",oauthParams.getSecret());
        params.put("client_id",oauthParams.getClientId());
        params.put("code",code);
        params.put("redirect_uri",oauthParams.getRedirectURI());
        params.put("grant_type","authorization_code");

        HttpURLConnection connection = HttpUrlConnectionHelper.constructConnection(oauthParams.getTokenEndpoint(),params);
        TokenResponse tokenResponse = HttpUrlConnectionHelper.getTokenResponse(connection);

        RMapCache<String,AccessToken> accessTokenMap = redissonClient.getMapCache(ACCESS_TOKEN_CACHE);
        UserDto user =  getUserWithToken(tokenResponse.getAccessToken());
        user.setRefreshToken(tokenResponse.getRefreshToken());

        if(tokenResponse.getAccessToken() == null){
            throw new TokenException("No access token");
        }
        if(tokenResponse.getRefreshToken()!=null){
            userService.save(user);
        }
        AccessToken accessToken = new AccessToken(tokenResponse.getAccessToken(),tokenResponse.getExpiresIn());
        accessTokenMap.put(user.getAccountId(),accessToken);

        return user;
    }

    public UserDto getUser(String accountId) throws TokenException, IOException {
        RMapCache<String,AccessToken> accessTokenMap =  redissonClient.getMapCache(ACCESS_TOKEN_CACHE);
        AccessToken accessToken = accessTokenMap.get(accountId);
        if(accessToken == null){
            throw new TokenException("No Access Token");
        }
        if(System.currentTimeMillis() > accessToken.getExpiresIn()){
            return getUserWithToken(refreshAccessToken(accountId));
        }
        return getUserWithToken(accessToken.getToken());
    }

    public String refreshAccessToken(String accountId) throws TokenException, IOException {
        UserDto user = userService.getUserByAccountIdAndType(accountId,oauthParams.getType());
        if(user == null || user.getRefreshToken() == null){
            throw new TokenException("No refresh token available");
        }

        AccessToken accessToken = getAccessToken(user.getRefreshToken());
        redissonClient.getMapCache(ACCESS_TOKEN_CACHE).put(accountId,accessToken);
        return accessToken.getToken();
    }

    public AccessToken getAccessToken(String refreshToken) throws IOException {
        Map<String,String> params = new HashMap<>();
        params.put("client_id",oauthParams.getClientId());
        params.put("client_secret",oauthParams.getSecret());
        params.put("refresh_token",refreshToken);
        params.put("grant_type","refresh_token");

        HttpURLConnection connection = HttpUrlConnectionHelper.constructConnection(oauthParams.getRefreshEndpoint(),params);
        TokenResponse tokenResponse = HttpUrlConnectionHelper.getTokenResponse(connection);
        return new AccessToken(tokenResponse.getAccessToken(),tokenResponse.getExpiresIn());
    }

    public UserDto getUserWithToken(String accessToken) throws IOException{
        HttpURLConnection connection = HttpUrlConnectionHelper
                .constructConnection(oauthParams.getUserInfoEndpoint(),accessToken);
        UserDto user =  HttpUrlConnectionHelper.getUserResponse(connection);
        user.setType(oauthParams.getType());
        return user;
    }
}
