package com.yicj.study.service;

import com.yicj.study.entity.Book;

public interface IDocumentService {
	//根绝id查询document
	public Book queryDocumentById(String id) ;
	//新增document
	public Integer insertDocument(Book book) ;
	//更新document
	public Integer updateDocument(Book book) ;
	//删除document
	public String deleteDocument(String id) ;
}
