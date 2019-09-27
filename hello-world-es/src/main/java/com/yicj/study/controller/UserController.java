package com.yicj.study.controller;

import com.yicj.study.models.UserEntity;
import com.yicj.study.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequestMapping("/book")
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserRepository repository;

	@RequestMapping("/addUser")
	public UserEntity addUser(@RequestBody UserEntity user) {
		return repository.save(user);
	}

	@RequestMapping("/findUser")
	public Optional<UserEntity> findUser(String id) {
		return repository.findById(id);
	}


}
