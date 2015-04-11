package com.mcc.XUtilsTest.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * user:mcc
 * Data:15-4-11
 */

/**
 * 分类对象
 */
@Table(name = "categories")
public class Category {
    @Id(column = "_id")
    private long id;
    @Column(column = "cName",defaultValue = "0")
    private String cName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
