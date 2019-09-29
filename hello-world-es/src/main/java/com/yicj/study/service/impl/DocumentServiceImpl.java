package com.yicj.study.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.yicj.study.common.PageInfo;
import com.yicj.study.service.IDocumentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private Gson gson;

	// 插入
	@Override
	public Integer insertDocument(String indexName, Map<String, Object> data) {
		IndexRequest indexRequest = new IndexRequest(indexName, indexName);
		indexRequest.source(gson.toJson(data), XContentType.JSON);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("插入document出错:", e);
		}
		return 1;
	}

	// 更新
	@Override
	public Integer updateDocument(String indexName, Map<String, Object> data, String id) {

		UpdateRequest updateRequest = new UpdateRequest(indexName, indexName, id);
		updateRequest.doc(data);
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("更新document出错:", e);
		}
		return 1;
	}

	// 删除
	@Override
	public Integer deleteDocument(String indexName, String id) {
		DeleteRequest deleteRequest = new DeleteRequest(indexName, indexName, id);
		try {
			client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("删除document出错:", e);
		}
		return 1;
	}
	
	@Override
	public Map<String, Object> queryDocumentById(String indexName, String id) {
		Map<String, Object> query = new HashMap<String, Object>() ;
		query.put("_id", id) ;
		List<Map<String, Object>> documents = this.queryDocument(indexName, query);
		if(documents.size() > 0) {
			return documents.get(0) ;
		}
		return new HashMap<String, Object>() ;
	}
	

	// 查询
	@Override
	public List<Map<String, Object>> queryDocument(String indexName, Map<String, Object> query) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.types(indexName);
		
		queryBuilder(null, null, query, indexName, searchRequest);
		try {
			SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit hit : resp.getHits().getHits()) {
				Map<String, Object> map = hit.getSourceAsMap();
				map.put("id", hit.getId());
				result.add(map);
			}
		} catch (IOException e) {
			log.error("查询document出错:", e);
		}
		return result;
	}

	private void queryBuilder(Integer pageIndex, Integer pageSize, Map<String, Object> query, String indexName,
			SearchRequest searchRequest) {
		if (query != null && !query.keySet().isEmpty()) {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			if (pageIndex != null && pageSize != null) {
				searchSourceBuilder.size(pageSize);
				if (pageIndex <= 0) {
					pageIndex = 0;
				}
				searchSourceBuilder.from((pageIndex - 1) * pageSize);
			}
			BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
			query.keySet().forEach(key -> {
				// ESDataTypeEnum type = ESDataTypeEnum.TEXT;
				boolBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));

			});
			searchSourceBuilder.query(boolBuilder);
			searchRequest.source(searchSourceBuilder);
		}
	}

	// 分页查询
	public PageInfo<Map<String, Object>> documentPage(Integer pageIndex, 
			Integer pageSize, String indexName,Map<String, Object> data) {
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>() ;
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.types(indexName);
		queryBuilder(pageIndex, pageSize, data, indexName, searchRequest);
		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit hit : response.getHits().getHits()) {
				Map<String, Object> map = hit.getSourceAsMap();
				map.put("id", hit.getId());
				result.add(map);
			}
			pageInfo.setPageSize(pageSize);
			pageInfo.setPageSize(pageIndex);
			pageInfo.setTotal(response.getHits().getTotalHits());
			pageInfo.setList(result);
		} catch (IOException e) {
			log.error("分页查询出错:",e);
		}
		return pageInfo;
	}
	//批量插入
	@Override
	public Integer batchInsertDocument(String indexName, List<Map<String, Object>> dataList) {
		BulkRequest bulkRequest = new BulkRequest()  ;
		dataList.forEach(data->{
			IndexRequest indexRequest = new IndexRequest(indexName,indexName) ;
			indexRequest.source(gson.toJson(data),XContentType.JSON) ;
			bulkRequest.add(indexRequest) ;
		});
		try {
			client.bulk(bulkRequest, RequestOptions.DEFAULT) ;
		} catch (IOException e) {
			log.error("批量插入出错:",e);
		}
		return dataList.size();
	}

	
	

}
