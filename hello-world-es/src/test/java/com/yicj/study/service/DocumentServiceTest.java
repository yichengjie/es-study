package com.yicj.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yicj.study.ESApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class DocumentServiceTest {
	private String indexName = "helloworld";

	@Autowired
	private IDocumentService documentService;

	@Test
	public void testInsertDocument() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", "易成杰");
		data.put("addr", "北京市顺义区后沙峪镇");
		data.put("email", "626659321@qq.com");
		documentService.insertDocument(indexName, data);
	}

	@Test
	public void testQueryDocument() {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("name", "成杰");
		List<Map<String, Object>> documents = documentService.queryDocument(indexName, query);
		System.out.println(documents);
	}
	
	
	@Test
	public void testQueryDocumentById() {
		String id = "niHte20BvlklB-6jBL2m" ;
		Map<String, Object> document = documentService.queryDocumentById(indexName, id) ;
		System.out.println(document);
	}
	
	
	
	@Test
	public void testUpdateDocument() {
		String id = "niHte20BvlklB-6jBL2m" ;
		Map<String, Object> data = new HashMap<String,Object>() ;
		data.put("name", "李四2") ;
		documentService.updateDocument(indexName, data, id) ;
	}
	

	@Test
	public void testBatchInsertDocument() {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> data1 = new HashMap<String, Object>();
		data1.put("name", "张三");
		data1.put("addr", "北京市朝阳区");
		data1.put("email", "698655551@163.com");
		data1.put("age", 25);
		/////////////////////
		Map<String, Object> data2 = new HashMap<String, Object>();
		data2.put("name", "李四");
		data2.put("addr", "沈阳市浑南区xx地");
		data2.put("email", "56668777@163.com");
		data2.put("age", 30);
		/////////////////////
		Map<String, Object> data3 = new HashMap<String, Object>();
		data3.put("name", "王五");
		data3.put("addr", "河南省南阳市卧龙区");
		data2.put("email", "66654481@162.com");
		data3.put("dept", "软件工程");
		/////////////////////
		Map<String, Object> data4 = new HashMap<String, Object>();
		data4.put("name", "赵六");
		data4.put("addr", "天津市");
		data4.put("email", "88922321@qq.com");
		data4.put("dept", "网络工程");
		dataList.add(data1) ;
		dataList.add(data2) ;
		dataList.add(data3) ;
		dataList.add(data4) ;
		documentService.batchInsertDocument(indexName, dataList);
	}

}
