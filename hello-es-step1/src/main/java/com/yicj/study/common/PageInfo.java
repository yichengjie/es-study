package com.yicj.study.common;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class PageInfo<T> {
	private Integer pageSize ;
	private Long total ;
	private List<Map<String, Object>> list ;
	
}
