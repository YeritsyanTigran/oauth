package com.TigranCorporations.oauth2.core.domain.mapper;

import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.TigranCorporations.oauth2.core.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity fromUserDtoToUserEntity(UserDto user);
    UserDto fromUserEntityToUserDto(UserEntity user);
}
