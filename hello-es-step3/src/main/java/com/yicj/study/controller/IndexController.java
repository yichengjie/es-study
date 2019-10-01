package com.yicj.study.controller;

import com.yicj.study.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/index")
public class IndexController {

	@Autowired
	private IIndexService helloService;

	@GetMapping("/createIndex")
	public boolean createIndex() {
		return helloService.createIndex();
	}

}
