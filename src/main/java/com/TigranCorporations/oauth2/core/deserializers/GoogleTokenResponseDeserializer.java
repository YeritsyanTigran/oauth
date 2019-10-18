package com.TigranCorporations.oauth2.core.deserializers;


import com.TigranCorporations.oauth2.controller.model.TokenResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class GoogleTokenResponseDeserializer extends JsonDeserializer<TokenResponse> {
    @Override
    public TokenResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TokenResponse response = new TokenResponse();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode accessTokeNode = node.get("access_token");
        JsonNode refreshTokenNode = node.get("refresh_token");
        JsonNode idTokenNode = node.get("id_token");
        JsonNode expiresInNode = node.get("expires_in");
        JsonNode scopeNode = node.get("scope");
        JsonNode tokenTypeNode = node.get("toke_type");

        if(accessTokeNode!=null){
            response.setAccessToken(accessTokeNode.textValue());
        }
        if(refreshTokenNode!=null){
            response.setRefreshToken(refreshTokenNode.textValue());
        }
        if(idTokenNode!=null){
            response.setTokenId(idTokenNode.textValue());
        }
        if(expiresInNode!=null){
            response.setExpiresIn(expiresInNode.intValue());
        }
        if(scopeNode!=null){
            response.setScope(scopeNode.textValue());
        }
        if(tokenTypeNode!=null){
            response.setTokenType(tokenTypeNode.textValue());
        }
        return response;

    }
}
