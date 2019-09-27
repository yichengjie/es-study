package com.yicj.study.repo;

import java.util.List;

import com.yicj.study.models.UserEntity;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserEntity,String> {

}
