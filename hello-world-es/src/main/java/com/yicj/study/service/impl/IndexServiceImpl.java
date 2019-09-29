package com.yicj.study.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yicj.study.service.IIndexService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexServiceImpl implements IIndexService {
	@Autowired
    private RestHighLevelClient client;
	
	@Override
	public boolean createIndex(String indexName) {
		try {
			CreateIndexRequest index = new CreateIndexRequest(indexName);
			Map<String, Object> properties = new HashMap<>();
			Map<String, Object> propertie = new HashMap<>();
			propertie.put("type", "text");
			propertie.put("index", true);
			propertie.put("analyzer", "ik_max_word");
			properties.put("field_name", propertie);
			XContentBuilder builder = JsonXContent.contentBuilder();
			builder.startObject()
			.startObject("mappings")
			.startObject("index_name")
			.field("properties", properties)
			.endObject()
			.endObject()
			.startObject("settings")
			.field("number_of_shards", 3)
			.field("number_of_replicas", 1)
			.endObject().endObject();
			index.source(builder);
			client.indices().create(index, RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
			log.error("创建Index报错...",e);
			return false;
		}
	}

	@Override
	public boolean isIndexExists(String indexName) {
		GetIndexRequest request = new GetIndexRequest() ;
		request.indices(indexName) ;
		try {
			return client.indices().exists(request, RequestOptions.DEFAULT) ;
		} catch (IOException e) {
			log.error("判断Index是否存在出错:",e);
			return false;
		}
		
	}

	@Override
	public boolean deleteIndex(String indexName) {
		DeleteIndexRequest index = new DeleteIndexRequest(indexName) ;
		try {
			client.indices().delete(index, RequestOptions.DEFAULT) ;
			return true;
		} catch (IOException e) {
			log.error("删除Index出错:" ,e);
			return false ;
		}
	}

}
