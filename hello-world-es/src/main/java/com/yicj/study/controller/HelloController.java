package com.yicj.study.controller;

import com.yicj.study.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/index")
public class HelloController {

	@Autowired
	private IHelloService helloService;

	@GetMapping("/createIndex")
	public boolean createIndex() {
		String indexName = "helloworld";
		return helloService.createIndex(indexName);
	}

}
