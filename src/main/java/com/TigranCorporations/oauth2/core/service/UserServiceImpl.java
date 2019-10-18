package com.TigranCorporations.oauth2.core.service;

import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.TigranCorporations.oauth2.core.domain.entity.UserEntity;
import com.TigranCorporations.oauth2.core.domain.mapper.UserMapper;
import com.TigranCorporations.oauth2.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto save(UserDto user) {
        if(user == null){
            return null;
        }
        UserEntity userToSave = userRepository.getByAccountIdAndType(user.getAccountId(),user.getType());
        if(userToSave!=null){
            userToSave.setRefreshToken(user.getRefreshToken());
        }else {
            userToSave = UserMapper.INSTANCE.fromUserDtoToUserEntity(user);
        }
        return UserMapper.INSTANCE.fromUserEntityToUserDto(userRepository.save(userToSave));
    }

    @Override
    public UserDto getUserByAccountIdAndType(String accountId, String type) {
        if(accountId == null || type == null){
            return null;
        }
        UserEntity user = userRepository.getByAccountIdAndType(accountId,type);
        return user == null ? null : UserMapper.INSTANCE.fromUserEntityToUserDto(user);
    }
}
