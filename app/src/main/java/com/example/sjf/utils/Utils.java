package com.example.sjf.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sjf.constants.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 工具类
 * Created by Administrator on 2016/9/20.
 */
public class Utils {

    /**
     * 判断网络是否链接(需要android.permission.ACCESS_NETWORK_STATE权限)false无连接true链接
     */
    public static boolean getNetState(Context activity) {
        boolean result = false;
        ConnectivityManager mConnectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info != null) {
            result = true;
        }
        return result;
    }

    /**
     * 计算listView高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    /**
     * 获取地址信息
     */
    public ArrayList<String> selectCityDataOfArea(String pid, SQLiteDatabase db) {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select * from area where parent_id= (?)";
        Cursor cursor = db.rawQuery(sql, new String[]{pid});
        while (cursor.moveToNext()) {
            String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
            list.add(region_name);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 判断sd卡中是否有地址数据库
     */
    public boolean hasDb(Context context, String name) {
        boolean result = false;
        String filePath = Environment.getExternalStorageDirectory() + Api.DATABASE_DIRE;// 数据库在SDCard中存放的路径
        // String fileName = "ecm_region.db";// 数据库名称
        String fileName = name;// 数据库名称
        // 判断数据库是否存在SDCard中
        if (new File(Environment.getExternalStorageDirectory() + Api.DATABASE_DIRE + name).exists()) {// 数据库已存在
            result = true;
        } else {// 数据库不存在存在
            boolean state = new Utils().copyFileFromAssets(context, filePath, fileName);
            // 数据库复制成功
            // 数据库复制失败
            result = state == true;
        }
        return result;
    }
    /**
     * 将地址数据库写入到sd卡中
     */
    public boolean copyFileFromAssets(Context context, String filepath, String fileName) {
        boolean result = false;
        try {
            File tmpFile = new File(filepath);
            if (!tmpFile.exists()) {
                tmpFile.mkdirs();
            }
            if (!new File(filepath + fileName).exists()) {
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = context.getAssets().open(fileName);
                // 输出流
                OutputStream os = new FileOutputStream(filepath + fileName);
                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                // 关闭文件流
                os.flush();
                os.close();
                is.close();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算gridView的高度
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            // 利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            // 利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows + 2);// 最后加上分割线总高度
        gridView.setLayoutParams(params);
    }


}
