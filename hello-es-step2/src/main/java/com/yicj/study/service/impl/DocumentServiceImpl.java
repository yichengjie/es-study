package com.yicj.study.service.impl;

import java.io.IOException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.common.EsConsts;
import com.yicj.study.common.RestClientHelper;
import com.yicj.study.entity.Book;
import com.yicj.study.service.IDocumentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements IDocumentService {
	private RestHighLevelClient client = RestClientHelper.getClient();
	// 插入
	@Override
	public Integer insertDocument(Book book) {
		try {
			IndexRequest indexRequest = new IndexRequest(EsConsts.INDEX_NAME, EsConsts.TYPE, book.getNumber());
			ObjectMapper mapper = new ObjectMapper();
			byte[] json = mapper.writeValueAsBytes(book);
			indexRequest.source(json, XContentType.JSON);
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("插入document出错:", e);
		}
		return 1;
	}


	// 更新
	@Override
	public Integer updateDocument(Book book) {
		try {
			UpdateRequest updateRequest = new UpdateRequest(EsConsts.INDEX_NAME, EsConsts.TYPE, book.getNumber());
			IndexRequest indexRequest = new IndexRequest(EsConsts.INDEX_NAME, EsConsts.TYPE, book.getNumber());
			ObjectMapper mapper = new ObjectMapper();
			byte[] json = mapper.writeValueAsBytes(book);
			indexRequest.source(json, XContentType.JSON);
			updateRequest.doc(indexRequest);
			client.update(updateRequest,RequestOptions.DEFAULT).getGetResult();
		} catch (IOException e) {
			log.error("更新document出错:", e);
		}
		return 1;
	}

	// 删除
	@Override
	public String deleteDocument(String id) {
		try {
			DeleteRequest deleteRequest = new DeleteRequest(EsConsts.INDEX_NAME, EsConsts.TYPE, id);
			DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
	        return delete.getResult().toString();
		} catch (IOException e) {
			log.error("删除document出错:", e);
			return null ;
		}
	}

	@Override
	public Book queryDocumentById(String id) {
		try {
			GetRequest getRequest = new GetRequest(EsConsts.INDEX_NAME, EsConsts.TYPE, id);
			GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			byte[] sourceAsBytes = getResponse.getSourceAsBytes();
			ObjectMapper mapper = new ObjectMapper();
			Book book = mapper.readValue(sourceAsBytes, Book.class);
			return book;
		} catch (IOException e) {
			log.error("根据id查询document报错");
			return null ;
		}
	}

}
