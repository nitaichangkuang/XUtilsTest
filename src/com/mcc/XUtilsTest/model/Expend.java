package com.mcc.XUtilsTest.model;

/**
 * user:mcc
 * Data:15-4-11
 */

import android.support.annotation.IdRes;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 支持信息的实体类
 */
//@Table 最终在内部就会生成 CREATE TABLE expends() 一个语句
@Table(name = "expends")//代表定义一个数据库表，代表当前类描述的是一个表，DbUtils会自动创建这个表
public class Expend {
    @Id(column = "_id") //id成员标量能够引用到 数据库表当中的主键，名字叫做 _id
    private long id;

    @Column(column = "money",defaultValue = "0")
    private float money;

    /**
     * 设置外键的部分，代表成员变量的数据，从指定类型中加载
     * 通过表内部的column 指向到Category表中的_id上面，进行唯一的设置
     */
    @Foreign(column = "cid",foreign = "_id")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column    //如果不指定名称，那么根据变量名来进行表的设置
    //如果采用这种不写名称的方式，当软件发布的时候，如果进行了“混淆”之后，
    // 成员变量就会被自动改成a,b,c,d.....这个时候列明就变了
    //推荐指定列明
    private long currentTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
