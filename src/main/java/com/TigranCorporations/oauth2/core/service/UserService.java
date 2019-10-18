package com.TigranCorporations.oauth2.core.service;

import com.TigranCorporations.oauth2.core.domain.dto.UserDto;

public interface UserService {
    UserDto save(UserDto user);
    UserDto getUserByAccountIdAndType(String accountId,String type);
}
