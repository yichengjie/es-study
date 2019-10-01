package com.yicj.study.common;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class RestClientHelper {

	private static RestHighLevelClient client;

	private RestClientHelper() {

	}

	public static RestHighLevelClient getClient() {
		if (client == null) {
			synchronized (RestClientHelper.class) {
				if (client == null) {
					client = new RestHighLevelClient(
							RestClient.builder(new HttpHost(EsConsts.HOST_NAME, EsConsts.PORT, "http")));
				}
			}
		}
		return client;
	}
}