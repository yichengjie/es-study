package com.yicj.study.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Service;
import com.yicj.study.common.EsConsts;
import com.yicj.study.common.RestClientHelper;
import com.yicj.study.service.IIndexService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexServiceImpl implements IIndexService {
	private RestHighLevelClient client = RestClientHelper.getClient();

	@Override
	public boolean createIndex(String indexName) {
		try {
			CreateIndexRequest request = new CreateIndexRequest(EsConsts.INDEX_NAME);
			buildSetting(request);
			buildIndexMapping(request);
			client.indices().create(request,RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
			log.error("创建Index报错...", e);
			return false;
		}
	}
	
	 //设置分片
    private void buildSetting(CreateIndexRequest request){
        request.settings(Settings.builder().put("index.number_of_shards",3)
        .put("index.number_of_replicas",2));
    }
    
    //设置index的mapping
    public void buildIndexMapping(CreateIndexRequest request){
        Map<String, Object> jsonMap = new HashMap<>();
        ////////////////////////////////////////////
        Map<String, Object> number = new HashMap<>();
        number.put("type", "text");
        ////////////////////////////////////////////
        Map<String, Object> price = new HashMap<>();
        price.put("type", "float" );
        ////////////////////////////////////////////
        Map<String, Object> title = new HashMap<>();
        title.put("type", "text");
        ////////////////////////////////////////////
        Map<String, Object> province = new HashMap<>();
        province.put("type", "text");
        ////////////////////////////////////////////
        Map<String, Object> publishTime = new HashMap<>();
        publishTime.put("type", "date");
        ////////////////////////////////////////////
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", number);
        properties.put("price", price);
        properties.put("title", title);
        properties.put("province", province);
        properties.put("publishTime", publishTime);
        Map<String, Object> book = new HashMap<>();
        book.put("properties", properties);
        jsonMap.put("books", book);
        request.mapping(EsConsts.TYPE, jsonMap);
    }

	@Override
	public boolean isIndexExists(String indexName) {
		try {
			GetIndexRequest request = new GetIndexRequest();
			request.indices(indexName);
			return client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("判断Index是否存在出错:", e);
			return false;
		}
	}

	@Override
	public boolean deleteIndex(String indexName) {
		try {
			GetIndexRequest getIndexRequest = new GetIndexRequest();
	        getIndexRequest.indices(EsConsts.INDEX_NAME);
	        if(client.indices().exists(getIndexRequest,RequestOptions.DEFAULT)) {
	            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(EsConsts.INDEX_NAME);
	            client.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
	        }
			return true;
		} catch (IOException e) {
			log.error("删除Index出错:", e);
			return false;
		}
	}

}
