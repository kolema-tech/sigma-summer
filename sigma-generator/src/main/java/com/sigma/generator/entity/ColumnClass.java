package com.sigma.generator.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-14:27
 * desc: 列的類
 **/
@Getter
@Setter
public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String columnName;
    /**
     * 数据库字段类型
     **/
    private String columnType;
    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String changeColumnName;
    /**
     * 数据库字段注释
     **/
    private String columnComment;
}
