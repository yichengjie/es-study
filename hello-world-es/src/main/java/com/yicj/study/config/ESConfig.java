package com.yicj.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//基础包的注释驱动配置，配置自动扫描的repositories根目录
@Configuration
@EnableElasticsearchRepositories("com.yicj.study.repo")
public class ESConfig {
	
}
