package com.yicj.study;

import com.yicj.study.models.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class EsApplicationTest {

    @Autowired
    private ElasticsearchTemplate template ;

    //创建索引，会根据Item类的@Document注解信息来创建
    @Test
    public void testCreateIndex() {
        template.createIndex(Item.class);
    }

}
