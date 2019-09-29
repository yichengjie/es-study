package com.yicj.study.service;

import java.util.HashMap;
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
	private String indexName = "helloworld" ;

	@Autowired
	private IDocumentService documentService ;
	
	@Test
	public void testInsertDocument() {
		Map<String, Object> data = new HashMap<String, Object>() ;
		data.put("name", "易成杰") ;
		data.put("addr", "北京市顺义区后沙峪镇") ;
		data.put("email", "626659321@qq.com") ;
		documentService.insertDocument(indexName, data) ;
	}
}
