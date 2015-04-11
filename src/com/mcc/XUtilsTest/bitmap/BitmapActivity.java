package com.mcc.XUtilsTest.bitmap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.mcc.XUtilsTest.R;
import com.mcc.XUtilsTest.adapter.BookAdapter;
import com.mcc.XUtilsTest.model.Book;

import java.util.LinkedList;

/**
 * user:mcc
 * Data:15-4-11
 */
public class BitmapActivity extends Activity {

    private LinkedList<Book> books;
    private BookAdapter adapter;

    private String[] urls = new String[]{
            "http://hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfab55480e9b68f8c5494ee7bf8.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d10b7d1fb30233fb80e7bec90ae.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/95eef01f3a292df5b2dbda17be315c6034a87393.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/8435e5dde71190ef33e5e4ddcc1b9d16fdfa6091.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/42a98226cffc1e17bdd57e6e4990f603738de903.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82e35dbf886e81800a19d843f8.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/bf096b63f6246b60483884c8eaf81a4c510fa253.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/728da9773912b31b30ee7f348418367adab4e129.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb327d0fe93db33c895d0430cec.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/c2cec3fdfc039245eef7470f8594a4c27d1e256b.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/ac6eddc451da81cb689b30d05066d016092431a9.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/0b7b02087bf40ad14d1101ea552c11dfa8eccec6.jpg",
            "http://f.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=3c4e049cc1cec3fd8b3ea073eeb3b302/6159252dd42a2834b1c7cf5b59b5c9ea15cebf79.jpg",
            "http://img2.niushe.com/upload/201304/19/14-21-43-20-26144.jpg",
            "http://a2.att.hudong.com/50/09/01300000098168135841092357166.jpg",
            "http://img2.100bt.com/upload/zl/20120817/1345175920846.jpg",
            "http://img.image.cn/2012/0325/14/fdc533fff794a4e9016317b2ee4f244d_middle.jpeg",
            "http://wenwen.soso.com/p/20110624/20110624024452-1526987252.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/eac4b74543a9822676675d708982b9014b90ebc7.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03464c20b5d63f8794a4c22683.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fb79c32b5f6f224f4a20a4dd46.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/b3119313b07eca809800649e932397dda1448346.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/3b292df5e0fe9925995f2e4f36a85edf8db17159.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/3812b31bb051f8194ca50936d8b44aed2f73e7ce.jpg",
            "http://pic-hzrb.hangzhou.com.cn/0/10/08/63/10086399_055559.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de8baadc6496eef01f3a297991.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c583690ba85d6277f9e2ff8ad.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/d043ad4bd11373f0f1e5223fa70f4bfbfaed04b3.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/38dbb6fd5266d0166b98d388942bd40734fa3588.jpg",
            "http://file.qqzzhh.com/upload/pic/201311/24/05/50/521b175c6ea56035.jpg%21600x600.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/562c11dfa9ec8a13c761e6edf503918fa0ecc040.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/6c224f4a20a44623aa31066b9a22720e0df3d7c4.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e561aad050ed7912397dd8c1c.jpg",
            "http://img4.duitang.com/uploads/item/201402/15/20140215012017_YmfyT.thumb.700_0.jpeg",
            "http://i.guancha.cn/news/2014/12/02/20141202104541822.jpg",
            "http://cdn.wanzhoumo.com/data/public/activity/2015/04/07_55/14283906287301.jpeg",
            "http://cdn.wanzhoumo.com/data/public/activity/2015/04/07_31/14283906259245.jpg",
            "http://cdn.wanzhoumo.com/data/public/activity/2015/04/07_86/14283906279451.jpg",
            "http://photo.aiutrip.com/pic/2010-5-25/22110623.jpg",
            "http://picapi.ooopic.com/10/61/68/21b1OOOPIC65.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=e1d3adcdf8edab646b724ac0c737af81/622762d0f703918fe530eecc523d269758eec4d7.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=20df54bda51ea8d395227304a70a30cf/d833c895d143ad4bc236a5c681025aafa40f069f.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/29381f30e924b8999565913e6d061d950a7bf65f.jpg",
            "http://img.ivsky.com/img/tupian/pre/201503/12/zhuangkuo_de_shanhe_fengguang.jpg",
            "http://img.ivsky.com/img/tupian/pre/201503/12/zhuangkuo_de_shanhe_fengguang-002.jpg",
            "http://img.ivsky.com/img/tupian/pre/201503/12/zhuangkuo_de_shanhe_fengguang-003.jpg",
            "http://p9.qhimg.com/t012cc4d9ccec35e07a.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3926641415,1117561589&fm=21&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2308063176,2061526697&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1153769095,1867407733&fm=21&gp=0.jpg",
            "http://d.3987.com/drzwtx_140415/001.jpg",
            "http://cdn2.image.apk.gfan.com/asdf/PImages/2014/5/16/827656_247458179-13ea-4fb1-b3f8-84ab54cc026c.jpg"

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ListView book_list = (ListView) findViewById(R.id.book_list);
        if (book_list != null) {
            int index = 0;
            books = new LinkedList<Book>();
            for (String url:urls){
                Book bk = new Book();
                bk.setCover(url);
                bk.setAuthor("google Inc.");
                bk.setTitle("Android Develop"+(index++));
                books.add(bk);
            }
            adapter = new BookAdapter(this, books);
            book_list.setAdapter(adapter);
        }
    }
}