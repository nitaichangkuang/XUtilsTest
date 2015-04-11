package com.mcc.XUtilsTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * user:mcc
 * Data:15-4-9
 */
@Target({ElementType.METHOD,ElementType.FIELD})//代表注解可以用到方法上
public @interface NotImplement {
    //所有的注解当中的变量或者是数值都要采用方法的书写形式
    //所有的value名字的内容，可以使用(数值)的形式
    //不需要完全写出（value=XXX）
    int value() default 4;
}
