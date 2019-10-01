package com.yicj.study.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yicj.study.ESApplication;
import com.yicj.study.service.IIndexService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class IndexServcieTest {
    @Autowired
    private IIndexService indexService ;

    @Test
    public void testCreateIndex() {
        boolean flag = indexService.createIndex();
        System.out.println("create index is success : " + flag);
    }
    
    @Test
    public void testIsIndexExists() {
        boolean flag = indexService.isIndexExists() ;
        System.out.println("index is exists : " + flag);
    }
    
    @Test
    public void testDeleteIndex() {
        boolean flag = indexService.deleteIndex() ;
        System.out.println("delete index success : " + flag);
    }

}
