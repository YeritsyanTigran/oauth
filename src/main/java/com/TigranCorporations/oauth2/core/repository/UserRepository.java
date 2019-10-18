package com.TigranCorporations.oauth2.core.repository;

import com.TigranCorporations.oauth2.core.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity getByAccountIdAndType(String accountId,String type);
}
