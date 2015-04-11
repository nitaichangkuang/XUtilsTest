package com.mcc.XUtilsTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Date;

public class MainActivity extends Activity {

    //定义一个TextView成员变量,所有以@开头的标记，叫做注解
    /**
     * @ViewInject 代表 TextView txtDate = (TextView)findViewById(R.id.txt_date);
     */
    @ViewInject(R.id.txt_date)
    private TextView txtDate;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //inject(Activity activity);
        ViewUtils.inject(this);
        txtDate.setText(new Date().toString());
    }
    @NotImplement
    @Override
    protected void onResume() {
        super.onResume();
    }
}
