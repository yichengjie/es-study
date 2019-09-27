package com.yicj.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.yicj.study.repo")
public class ESApplication {

    public static void main(String[] args) {

        SpringApplication.run(ESApplication.class,args) ;
    }

}
