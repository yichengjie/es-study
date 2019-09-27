package com.yicj.study.models;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id ;
	private String name ;
	private String author ;
}
