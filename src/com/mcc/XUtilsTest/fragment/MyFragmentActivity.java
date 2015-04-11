package com.mcc.XUtilsTest.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ResInject;
import com.mcc.XUtilsTest.R;

/**
 * user:mcc
 * Data:15-4-9
 */
@ContentView(R.layout.activity_fragment)//这个注解代表设置布局
public class MyFragmentActivity extends FragmentActivity {
    /**
     * 代表获取应用程序设置的名称
     */
    @ResInject(id = R.string.app_name, type = ResType.String)
    private String appName;

    @ResInject(id = R.drawable.ic_launcher,type = ResType.Drawable)
    private Drawable iconLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //所有的注解要想生效，必须先调用ViewUtils.inject()
        ViewUtils.inject(this);

        //所有的注解的对象全部初始化了。
        setTitle(appName);

    }
}