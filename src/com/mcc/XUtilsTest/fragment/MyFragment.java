package com.mcc.XUtilsTest.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfoBuilder;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.mcc.XUtilsTest.R;
import com.mcc.XUtilsTest.model.Category;
import com.mcc.XUtilsTest.model.Expend;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * user:mcc
 * Data:15-4-9
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyFragment extends Fragment {
    @ViewInject(R.id.button1)
    private Button button1;
    @ViewInject(R.id.button2)
    private Button button2;
    @ViewInject(R.id.button3)
    private Button button3;

    @ViewInject(R.id.my_list_view)
    private ListView my_list_view;
    private DbUtils dbUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一参数，上下文
        //第二个参数，数据库名称
        //第三个参数 数据库版本号
        //第四个参数 数据库升级接口，等同于SQLiteOpenHelper中的onUpgrade
        //Ctrl+Alt+F,装成员变量
        dbUtils = DbUtils.create(
                getActivity(),
                "myRecord.db",
                2,//增加了分类信息，因此进行升级
                new DbUtils.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
                        if(oldVersion==1&&newVersion==2){
                            //执行非查询操作
                            try {
                                db.execNonQuery("alter table expends add column cid long default 0");
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_test, container, false);
        //对于在Fragment当中成员变量的UI控件
        //采用inject(Object object,View view)
        //这种方法就可以了，实际上object就是自身的Fragment
        ViewUtils.inject(this, inflate);

        return inflate;
    }

    //当activity被创建时
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button1.setText("登陆");
        button2.setText("注册");
        button3.setText("忘记密码");

    }

    //获取到焦点，当获取到焦点的时候执行数据库语句
    @Override
    public void onResume() {
        super.onResume();
        try {
            //findAll(class)这个方法，让DbUtils查询指定实体类代表的记录
            //对应的SQL语句 select * from expends;
            List<Expend> expends = dbUtils.findAll(Expend.class);
            //expends是所有的数据
            if (expends != null) {
                for (Expend expend : expends) {
                    long currentTime = expend.getCurrentTime();
                    float money = expend.getMoney();
                    Log.i("DbUtils", "Record:" + currentTime + "--->" + money);
                }
            }

            //TODO 查询特定的一条数据
            //根据表中主键的值，来进行查找，未找到， 返回 null
            /**
             * 第一个参数类型，第二个参数条件
             */
            Expend t1 = dbUtils.findById(Expend.class, 1l);
            if (t1 != null) {
                Log.i("DbUtils", "ID 1 :" + t1.getMoney());
            }

            //查找特定范围的记录
            //select * from expends where money between 1000 and 2000
            //对于上面的 SQL采用finAll(selector)
            WhereBuilder builder = WhereBuilder.b();
            //直接书写表达式的方式
            builder.expr("money between 100 and 1000");

            builder.and("currentTime", ">", 1400000000000L);
            //b("money",">",1000);
            //and("category","=","taxt");
            List<Expend> all = dbUtils.findAll(Selector.from(Expend.class)
//                    .where("_id","=",1L)
                            .where(builder)
            );
            if (all != null) {
                for (Expend expend : all) {
                    Log.i("DbUtils", "All:" + expend.getMoney());
                }
            }
            //findFirst(selector) 通过条件查找第一个记录
            //例如，按照排序之后的记录查找，查找第一个记录，相当于取出最大值，最小值。
            Expend money = dbUtils.findFirst(
                    Selector.from(Expend.class)
                            .orderBy("money", true) //arderBy指定的是(String column,boolean desc),true表示降序排序
            );
            if (money != null) {
                Log.i("DbUtils", "Money:" + money.getMoney());
            }
            //TODO 获取特定的一些数据
            //DbModel 包含的局势获取的每一列的数据，保存的就是字段数据。
            DbModel model = dbUtils.findDbModelFirst(
                    DbModelSelector.from(Expend.class)
                            .orderBy("money", true)//以money降序排列
                            .select("money", "_id")//获取指定的列，类似于 select * from,select money from
            );
            if (model != null) {
                float money1 = model.getFloat("money");
                long id = model.getLong("_id");

                Log.i("DbUtils", "model money:" + money1 + "--->" + id);
            }
            //案例：1，取记录个数 select count(*) from xxxx 返回的是数字个数
            DbModel dbModelFirst = dbUtils.findDbModelFirst(
                    DbModelSelector.from(Expend.class)
                            .select("count(*)")
            );

//            dbUtils.findDbModelAll();


            if (dbModelFirst != null) {
                //获取列的名称与数据
                HashMap<String, String> dataMap = dbModelFirst.getDataMap();
                Set<String> keySet = dataMap.keySet();
                for (String key : keySet) {
                    String value = dataMap.get(key);
                    Log.i("DbUtils", "Count:" + key + "---->" + value);
                }
            }
            if (money != null) {
                //修改 ---------------数据库更新操作--------------------
                //1,第一种方式--->update 表 set columnName1=value1,....where _id=1
                //findFirst()   这种方法隐含了 id 的判断
                //第一个参数 就是表的对象实体 Expend 的实际数据对象
                money.setMoney(3000);
                dbUtils.update(money, "money");
            }

            //测试没有id，而是新的对象，能否进行更新操作。不能更新
            Expend expend = new Expend();
            expend.setId(50);
            expend.setMoney(4000);
            expend.setCurrentTime(System.currentTimeMillis());
            dbUtils.update(expend, "money", "currentTime");


//            dbUtils.updateAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        //采用where语句的方式，进行数据的更新,将数据库中的所有为1000的数据都更新成了1500
        //TODO 检查更新一条或者是多条的情况
        Expend ex = new Expend();
        ex.setMoney(1500);
        //使用WhereBuilder
        WhereBuilder builder = WhereBuilder.b("money", "=", "1000");

        try {
            //以下代码更新指定记录
//            dbUtils.update(ex, builder, "money");
            //第二个参数设置为null,全部都会被更新
            dbUtils.update(ex, (WhereBuilder)null, "money");
        } catch (DbException e) {
            e.printStackTrace();
        }

        //updateAll
        //updateAll使用WhereBuilder情况  我还没有测试-----------------------------------------------------
        List<Expend> es = new LinkedList<Expend>();
        Expend expend = new Expend();
        expend.setMoney(100);
        es.add(expend);

        expend = new Expend();
        expend.setMoney(10);
        es.add(expend);
        try {
            dbUtils.updateAll(es,(WhereBuilder)null,"money");
        } catch (DbException e) {
            e.printStackTrace();
        }

        ///删除操作
//        dbUtils.delete();
//        dbUtils.deleteAll();

    }

    @OnClick(R.id.button2)//设置按钮的点击事件
    public void btn2Onclick(View view) {

        // TODO 存数据
        Expend todayExpend1 = new Expend();
        todayExpend1.setCurrentTime(System.currentTimeMillis());
        todayExpend1.setMoney(1000);

        //TODO 设置分类信息
        Category category =new Category();
        category.setcName("你好");

        //TODO 检查这样写完能否保存
        todayExpend1.setCategory(category);


        //存数据
        try {
            //相当于添加操作
            dbUtils.save(todayExpend1);
            //希望添加或者获取，修改数据
//            dbUtils.saveOrUpdate(todayExpend1);
            //批量添加多个记录
            //参数 List<实体类型>
            //用于批量保存列表数据
            dbUtils.saveAll(new LinkedList<Expend>());

//            dbUtils.saveBindingId();

        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭数据库
        dbUtils.close();
    }

    @OnClick(R.id.button1)//设置按钮的点击事件
    public void btn1Onclick(View view) {
        Toast.makeText(getActivity(), "btn1 onclick", Toast.LENGTH_SHORT).show();
        //HttpUtils进行网络的加载
        //必须new一个 HttpUtils才可以进行网络的加载
        //通常可以作为成员变量，new一次就好了，之后的网络请求，就调用方法
        //无参构造，默认15秒超时
        //一个参数的就是设置超时时间，单位毫秒

//        HttpUtils httpUtils = new HttpUtils();

        HttpUtils httpUtils = new HttpUtils(10 * 1000);
        //设置同时能顾并发网络连接的数量
        httpUtils.configRequestThreadPoolSize(5);
        //user-Agent用于向服务器发送的字段的值
        httpUtils.configUserAgent("androidClient");

        //进行请求，访问百度
        HttpHandler<String> handler = httpUtils.send(HttpRequest.HttpMethod.GET,
                "http://www.baidu.com",
                new RequestCallBack<String>()

                {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("HttpUtils", "success" + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("HttpUtils", "failure" + msg);
                    }
                }
        );

        //实现post请求,使用带有RequestParams 这个send方法，就可以进行数据的提交
        //请求的参数 user=admin&password=123
        RequestParams requestParams = new RequestParams();
        //1,实现 key = value&key=value的形式（标准浏览器提交数据的形式）
        //最简单key=value方式，就直接（"key","value"）
        //post请求基本的数据提交方式
        requestParams.addBodyParameter("user", "admin");
        requestParams.addBodyParameter("password", "123");
        httpUtils.send(HttpRequest.HttpMethod.POST,
                "http://www.baidu.com/",
                requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("HttpUtils", "success" + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("HttpUtils", "failure" + msg);
                    }
                });

        //////////////////////////////
        //实现文件上传的POST请求
        //同样是send方法，并且 需要制定RequestParams,和普通的POST一致
        /**
         * http上传文件功能，提交的内部格式：multiPart/form-data
         *
         *格式的案例
         * 如果普通提交采用user=admin&password=124
         * 那么multiPart/form-data格式
         * content-type:multiPart/form-data;boundcy=AbCDe
         *
         * 一下是数据:
         * --AbCDe
         * Content-Type:name=user
         * admin
         * --AbCDe
         * Content-Type:name=password
         *
         * 123
         * --AbCDe
         *
         */
        //实现以上格式，对于xUtils当中的HttpUtils只要再RequestParams添加一个文件（File）的参数
        //实现文件上传功能
        RequestParams params = new RequestParams();
        //可以使用File类对象作为参数传递；
        //FIle必须刻度，必须是文件，不能设置目录上传
        params.addBodyParameter("file", new File("需要上传的文件"));
        //如果需要在文件上传时，标准文件的格式，可以设置三个参数的addBodyParamter来完成
        //第三个参数，是类型参数，代表文件的类型
        //文件类型采用标准的MIME类型。 类型格式 大分类、小内容
        //例如：PNG 图片，类型 image/png
        //例如：GIF 图片，类型 image/gif
        //作业：Android APK 类型
        params.addBodyParameter("file", new File("需要上传的文件"), "image/png");
        httpUtils.send(HttpRequest.HttpMethod.POST,
                "http://www.baidu.com",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });

        /////////////////文件的下载//////////////////////////
        //HttpUtils download方法，主要用于下载数据

        //获取存储卡的文件位置

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, "img.png");


            httpUtils.download("https://www.baidu.com/img/bdlogo.png", file.getPath(), new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {

                }

                @Override
                public void onFailure(HttpException error, String msg) {

                }
            });
        }
    }

    @OnItemClick(R.id.my_list_view)//设置ListView 的Item的点击事件
    public void listItemClick(AdapterView<?> parent, View view, int pos, long id) {

    }


}