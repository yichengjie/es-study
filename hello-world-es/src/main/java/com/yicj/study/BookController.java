package com.yicj.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yicj.study.models.Book;
import com.yicj.study.repo.BookRepository;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/book")
@RestController
@Slf4j
public class BookController {
	
	@Autowired
	private BookRepository bookRepository ;
	
	public ResponseEntity<String>  indexDoc(@RequestBody Book book){
		log.info("book =====> " + book);
		bookRepository.save(book) ;
		return new ResponseEntity<>("save executed !", HttpStatus.OK) ;
	}
	
	
}
