package com.yicj.study.models;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@ToString
@Document(indexName = "mymayikt", type = "user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private int sex;
	private int age;
}
