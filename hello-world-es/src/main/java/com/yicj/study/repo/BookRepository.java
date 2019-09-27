package com.yicj.study.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yicj.study.models.Book;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
	Book findByName (String name) ;
	List<Book> findByAuthor(String author) ;
	Book findBookById(String id) ;
}
