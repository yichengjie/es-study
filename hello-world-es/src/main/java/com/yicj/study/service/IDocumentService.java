package com.yicj.study.service;

import java.util.List;
import java.util.Map;

import com.yicj.study.common.PageInfo;

public interface IDocumentService {
	//新增document
	public Integer insertDocument(String indexName, Map<String, Object> data) ;
	//更新document
	public Integer updateDocument(String indexName, Map<String, Object> data, String id) ;
	//删除document
	public Integer deleteDocument(String indexName, String id) ;
	//查询document
	public List<Map<String, Object>> queryDocument(String indexName, Map<String, Object> query) ;
	//分页查询
	public PageInfo<Map<String, Object>> documentPage(Integer pageIndex, 
			Integer pageSize, String indexName,Map<String, Object> data)  ;
	//批量插入
	public Integer batchInsertDocument(String indexName, List<Map<String, Object>> dataList) ;
}
