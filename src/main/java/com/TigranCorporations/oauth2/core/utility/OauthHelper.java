package com.TigranCorporations.oauth2.core.utility;

import com.TigranCorporations.oauth2.configuration.OauthParams;
import com.TigranCorporations.oauth2.controller.model.Payload;
import com.TigranCorporations.oauth2.controller.model.TokenResponse;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@Component
public class OauthHelper {
    public static final String REFRESH_TOKEN_CACHE = "REFRESH_TOKEN";

    @Autowired
    private OauthParams oauthParams;

    @Autowired
    private RedissonClient redissonClient;

    public Payload getCredentials(String code) throws IOException, TokenException {
        URL url = new URL(oauthParams.getTokenEndpoint());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        Map<String,String> params = new HashMap<>();
        params.put("client_secret",oauthParams.getSecret());
        params.put("client_id",oauthParams.getClientId());
        params.put("code",code);
        params.put("redirect_uri",oauthParams.getRedirectURI());
        params.put("grant_type","authorization_code");

        writeParamsToUrl(params,connection);
        TokenResponse response = getOutput(connection);

        RMapCache<String,String> refreshTokenCache = redissonClient.getMapCache(REFRESH_TOKEN_CACHE);
        Payload payload =  getPayload(response.getTokenId());
        if(response.getRefreshToken()!=null) {
            refreshTokenCache.computeIfAbsent(payload.getSub(),k-> response.getRefreshToken());
        }
        return payload;
    }

    public Payload refreshTokenId(String sub) throws TokenException, IOException {
        Object refreshToken = redissonClient.getMapCache(REFRESH_TOKEN_CACHE).get(sub);
        if(refreshToken == null){
            throw new TokenException("No refresh token available");
        }
        URL url = new URL(oauthParams.getRefreshEndpoint());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        Map<String,String> params = new HashMap<>();
        params.put("client_id",oauthParams.getClientId());
        params.put("client_secret",oauthParams.getSecret());
        params.put("refresh_token",refreshToken.toString());
        params.put("grant_type","refresh_token");

        writeParamsToUrl(params,connection);
        TokenResponse response = getOutput(connection);
        return getPayload(response.getTokenId());

    }

    public static TokenResponse getOutput(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine())!=null){
            content.append(inputLine);
        }
        in.close();
        return new ObjectMapper().readValue(content.toString(), TokenResponse.class);
    }

    public static void writeParamsToUrl(Map<String,String> params,HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParametrStringBuilder.getParamsString(params));
        out.flush();
        out.close();
    }

    public Payload getPayload(String tokenId) throws IOException{
        String[] parts = tokenId.split("\\.");
        Payload payload =  new ObjectMapper().readValue(new String(Base64.getDecoder().decode(parts[1])), Payload.class);
        payload.setTokenId(tokenId);
        return payload;
    }
}
