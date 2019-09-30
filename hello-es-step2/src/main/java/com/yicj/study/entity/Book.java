package com.yicj.study.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Book {
    private String number;
    private Double price;
    private String title;
    private String province;
    private Date publishTime;
}