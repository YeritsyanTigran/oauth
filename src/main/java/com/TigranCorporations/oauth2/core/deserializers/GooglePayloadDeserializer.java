package com.TigranCorporations.oauth2.core.deserializers;

import com.TigranCorporations.oauth2.controller.model.Payload;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class GooglePayloadDeserializer extends JsonDeserializer<Payload> {
    @Override
    public Payload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Payload payload = new Payload();

        JsonNode email = node.get("email");
        JsonNode name = node.get("name");
        JsonNode sub = node.get("sub");
        JsonNode exp = node.get("exp");

        if(email!=null){
            payload.setEmail(email.textValue());
        }
        if(name!=null){
            payload.setName(name.textValue());
        }
        if(sub!=null){
            payload.setSub(sub.textValue());
        }
        if(exp!=null){
            payload.setExp(exp.intValue());
        }
        return payload;
    }
}
