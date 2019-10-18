package com.TigranCorporations.oauth2.core.deserializers;

import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class UserDtoDeserializer extends JsonDeserializer<UserDto> {
    @Override
    public UserDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        UserDto user = new UserDto();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode accountId = node.get("id");
        JsonNode name = node.get("name");

        if(accountId!=null){
            user.setAccountId(accountId.textValue());
        }
        if(name!=null){
            user.setName(name.textValue());
        }
        return user;
    }
}
