package com.mcc.XUtilsTest.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.mcc.XUtilsTest.R;
import com.mcc.XUtilsTest.model.Book;

import java.io.File;
import java.util.List;

/**
 * user:mcc
 * Data:15-4-11
 */
//Ctrl+i 快速生成方法
public class BookAdapter extends BaseAdapter {
    private Context context;
    private List<Book> books;
    private LayoutInflater inflater;
    /**
     * 图片加载工具
     */
    private BitmapUtils bitmapUtils;


    public BookAdapter(Context context, List<Book> books) {
        //健壮的写法
        if (context == null) {
            throw new IllegalArgumentException("context must not null");
        }
        this.context = context;

        this.books = books;
        inflater = LayoutInflater.from(context);
        //BitmapUtils构造有多种形式，
        //1,采用默认的构造，不使用文件缓存
        bitmapUtils = new BitmapUtils(context);
        //2,两个参数的构造，指定文件的缓存目录
        File cacheFolder = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //获取存储卡上面Android系统分配给软件的特定缓存目录
            File cacheDir = context.getExternalCacheDir();
            cacheFolder = new File(cacheDir, "images");
        } else {
            File cacheDir = context.getCacheDir();
            cacheFolder = new File(cacheDir, "images");
        }
        if (!cacheFolder.exists()) {
            //完整创建文件夹
            cacheFolder.mkdirs();
        }
        //创建BitmapUtils并且指定文件的缓存路径
        bitmapUtils = new BitmapUtils(context, cacheFolder.getAbsolutePath());

        //设置默认的“加载中”图片，这个图片是全局使用的，
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        //设置加载失败的图片
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.btn_home_right_top_ad);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (books != null) {
            ret = books.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Object ret = null;
        if (books != null) {
            ret = books.get(position);
        }
        return ret;
    }

    /**
     * 当数据库显示数据 CursorAdapter时，这个方法返回的就是记录的ID
     * 另一种情况：ListView显示纯静态内容，并且ListView设置为多选的情况下，这个ID才有作用
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View ret = null;
        if (convertView != null) {
            ret = convertView;
        } else {
            //  inflate 方法，推荐使用三个参数的，第二个参数就是parent,第三个参数
            //代表是否要自动的添加到parent当中。
            ret = inflater.inflate(R.layout.item_book, parent, false);
        }
        ViewHolder holder = (ViewHolder) ret.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.imgCover = (ImageView) ret.findViewById(R.id.item_book_cover);
            holder.txtAuthor = (TextView) ret.findViewById(R.id.item_book_author);
            holder.txtTitle = (TextView) ret.findViewById(R.id.item_book_name);
            ret.setTag(holder);
        }
        Book book = books.get(position);
        String author = book.getAuthor();
        if (author != null) {
            holder.txtAuthor.setText(author);
        }
        String title = book.getTitle();
        if (title != null) {
            holder.txtTitle.setText(title);
        }
        //图片网址
        String cover = book.getCover();
        if (cover != null) {
            //简单的形式，通过网络加载图片，并且显示到imgView上面
            //第一个参数是一个泛型，支持所有的view控件
            //如果第一个参数是ImageView或者ImageButton,那么
            //直接设置setImageBitmap相当于src属性
            //如果是其他View类型，会自动设置background。
            bitmapUtils.display(holder.imgCover, cover);
            //TODO 加载图片
        }

        return ret;
    }

    /**
     * static 的类，在Adapter类加载的时候，自动的被虚拟机加载了，会快一些。
     */
    private static class ViewHolder {
        public ImageView imgCover;
        public TextView txtTitle;
        public TextView txtAuthor;

    }
}
