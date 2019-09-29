package com.yicj.study.service;

public interface IHelloService {

	//添加Index
	public boolean createIndex(String indexName) ;
	//判断Index是否存在
	public boolean isIndexExists(String indexName) ;
	//删除Index
	public boolean deleteIndex(String indexName) ;
	
}
