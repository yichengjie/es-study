package com.yicj.study.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.yicj.study.ESApplication;
import com.yicj.study.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class DocumentServiceTest {
	@Autowired
	private IDocumentService documentService;
	@Test
	public void testInsertDocument() {
		Book book = new Book() ;
		documentService.insertDocument(book) ;
	}
	@Test
	public void testInsertDocument2() {
		Book book = new Book() ;
		documentService.insertDocument(book);
	}
	@Test
	public void testInsertDocument3() {
		Book book = new Book() ;
		documentService.insertDocument(book);
	}
	@Test
	public void testQueryDocumentById() {
		String id = "niHte20BvlklB-6jBL2m" ;
		Book book = documentService.queryDocumentById(id);
		System.out.println(book);
	}
	
	@Test
	public void testUpdateDocument() {
		Book book = new Book() ;
		documentService.updateDocument(book) ;
	}
}
