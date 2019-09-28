package com.yicj.study.controller;

import com.google.gson.Gson;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class IndexController {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Gson gson;

    @GetMapping("/order/getById/{id}")
    public Map<String, Object> getOrder(@PathVariable("id") String id) throws IOException {
        GetRequest getRequest = new GetRequest("order", "_doc", id);
        Map map = new HashMap();
        GetResponse response = null;
        response = client.get(getRequest, RequestOptions.DEFAULT);
        if (response.isExists()) {
            map.put("success", true);
            map.put("data", response.getSource());
        } else {
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/order/create")
    public Map<String, Object> createOrder(@RequestParam("buyer") String buyer,
                                           @RequestParam("date") String date,
                                           @RequestParam("totalPrice") Double total,
                                           @RequestParam("products") String products,
                                           @RequestParam("id") String id) throws IOException {
        Map map = new HashMap();
        XContentBuilder builder = null;
        IndexRequest request = new IndexRequest("order");
        List<Product> productList = gson.fromJson(products, new TypeToken<List<Product>>() {
        }.getType());
        List<Map<String, Object>> list = productList.stream().map(e -> {
            Map<String, Object> temp = new HashMap<>();
            Field[] fields = e.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                try {
                    temp.put(f.getName(), f.get(e));
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
            return temp;
        }).collect(Collectors.toList());
        builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("buyer", buyer).field("date", date)
                .field("totalPrice", total)

                .field("products", list)
                .endObject();
        request.id(id).opType("create").source(builder);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        map.put("status", response.status());
        return map;
    }

    @DeleteMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id) throws IOException {
        DeleteRequest request = new DeleteRequest("order", id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.status().name();
    }

}
