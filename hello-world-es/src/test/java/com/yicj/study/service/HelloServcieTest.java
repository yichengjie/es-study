package com.yicj.study.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yicj.study.ESApplication;
import com.yicj.study.service.IHelloService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class HelloServcieTest {

    @Autowired
    private IHelloService helloService ;

    @Test
    public void testCreateIndex() {
    	String indexName = "helloworld" ;
        boolean flag = helloService.createIndex(indexName);
        System.out.println("create index is success : " + flag);
    }
    
    @Test
    public void testIsIndexExists() {
    	String indexName = "helloworld" ;
        boolean flag = helloService.isIndexExists(indexName) ;
        System.out.println("index is exists : " + flag);
    }
    
    @Test
    public void testDeleteIndex() {
    	String indexName = "helloworld" ;
        boolean flag = helloService.deleteIndex(indexName) ;
        System.out.println("delete index success : " + flag);
    }

}
