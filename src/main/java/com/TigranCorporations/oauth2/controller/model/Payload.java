package com.TigranCorporations.oauth2.controller.model;

import com.TigranCorporations.oauth2.core.deserializers.GooglePayloadDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = GooglePayloadDeserializer.class)
public class Payload {
    private String name;
    private String email;
    private String sub;
    private String tokenId;
    private Integer exp;
}
