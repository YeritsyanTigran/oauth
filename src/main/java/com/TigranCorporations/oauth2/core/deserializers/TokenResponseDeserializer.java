package com.TigranCorporations.oauth2.core.deserializers;


import com.TigranCorporations.oauth2.controller.model.TokenResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TokenResponseDeserializer extends JsonDeserializer<TokenResponse> {
    @Override
    public TokenResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TokenResponse response = new TokenResponse();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode accessTokeNode = node.get("access_token");
        JsonNode refreshTokenNode = node.get("refresh_token");
        JsonNode expiresInNode = node.get("expires_in");

        if(accessTokeNode!=null){
            response.setAccessToken(accessTokeNode.textValue());
        }
        if(refreshTokenNode!=null){
            response.setRefreshToken(refreshTokenNode.textValue());
        }
        if(expiresInNode!=null){
            response.setExpiresIn(expiresInNode.longValue());
        }
        return response;

    }
}
