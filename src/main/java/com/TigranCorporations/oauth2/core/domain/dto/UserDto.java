package com.TigranCorporations.oauth2.core.domain.dto;

import com.TigranCorporations.oauth2.core.deserializers.UserDtoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonDeserialize(using = UserDtoDeserializer.class)
@EqualsAndHashCode(callSuper = true)
public class UserDto extends IdentifierDto {
    private String name;
    private String type;
    private String refreshToken;
    private String accountId;
}
