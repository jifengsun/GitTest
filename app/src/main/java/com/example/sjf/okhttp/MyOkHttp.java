package com.example.sjf.okhttp;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求工具类
 */

public class MyOkHttp {
    private static MyOkHttp mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private MyOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取网络请求实例
     * @return MyOkHttp
     */
    public static MyOkHttp getInstance() {
        if (mInstance == null) {
            synchronized (MyOkHttp.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttp();
                }
            }
        }
        return mInstance;
    }

    public interface RequestCallBack {
        /**
         * 请求成功
         *
         * @param data 返回的结果
         */
        void success(String data);

        /**
         * 请求失败
         *
         * @param request 请求的对列
         * @param e       错误信息
         */
        void fail(Request request, Exception e);
    }

    /**
     * 使用Get方式进行请求
     *
     * @param url           请求地址
     * @param callBack      请求的回调
     * @param loadingDialog 等待框
     */
    public void get(String url, final RequestCallBack callBack, Dialog loadingDialog) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        final Request request = new Request.Builder().url(url).get().build();
        call(request, callBack, loadingDialog);
    }

    /**
     * 使用Get方式进行请求
     *
     * @param url           请求地址
     * @param callBack      请求的回调
     * @param loadingDialog 等待框
     */
    public void get(String url, final RequestCallBack callBack, Dialog loadingDialog, boolean isAddCookie) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        Request request = null;
        if (isAddCookie) {
            request = new Request.Builder().url(url)
                    .addHeader("cookie", "PHPSESSID=").build();
        } else {
            request = new Request.Builder().url(url).get().build();
        }
        call(request, callBack, loadingDialog);
    }

    /**
     * 使用Post方式进行请求
     *
     * @param url           请求地址
     * @param params        请求的参数
     * @param callBack      请求的回调
     * @param loadingDialog 等待框
     */
    public void post(String url, Map<String, String> params, final RequestCallBack callBack, Dialog loadingDialog, boolean isAddCookie) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        MediaType requestType
                = MediaType.parse("application/json;charset=utf-8");
        Request request = null;
        FormBody.Builder requestBodyPost = new FormBody.Builder();
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = params.get(key);
            requestBodyPost.add(key, value);
        }
//        if (params == null) {
//            request = new Request.Builder().url(url).build();
//        } else {
//            String content = "";
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> item = iterator.next();
//                content += item.getKey() + "=" + item.getValue();
//                if (iterator.hasNext()) {
//                    content += "&";
//                }
//            }
//            RequestBody body = RequestBody.create(requestType, content);
        request = new Request.Builder().url(url).post(requestBodyPost.build()).build();
//        }
        call(request, callBack, loadingDialog);
//        MediaType requestType
//                = MediaType.parse("application/json; charset=utf-8");
//        Request request = null;
//        if (params == null) {
//            request = new Request.Builder().url(url).build();
//        } else {
//            String content = "";
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> item = iterator.next();
//                content += item.getKey() + "=" + item.getValue();
//                if (iterator.hasNext()) {
//                    content += "&";
//                }
//            }
//            RequestBody body = RequestBody.create(requestType, content);
//            if (isAddCookie) {
//                request = new Request.Builder().url(url)
//                        .addHeader("cookie", "user=" + UserRelated.user_token).post(body).build();
//            } else {
//                request = new Request.Builder().url(url).post(body).build();
//            }
//        }
//        call(request, callBack, loadingDialog);
    }

    /**
     * 使用Post方式进行请求
     * @param url             请求地址
     * @param requestBodyPost 请求的参数
     * @param callBack        请求的回调
     * @param loadingDialog   等待框
     */
    public void post(String url, RequestBody requestBodyPost, final RequestCallBack callBack, Dialog loadingDialog, boolean isAddCookie) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        MediaType requestType
                = MediaType.parse("application/json; charset=utf-8");
        Request request = null;

//        if (params == null) {
//            request = new Request.Builder().url(url).build();
//        } else {
//            String content = "";
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> item = iterator.next();
//                content += item.getKey() + "=" + item.getValue();
//                if (iterator.hasNext()) {
//                    content += "&";
//                }
//            }
//            RequestBody body = RequestBody.create(requestType, content);
        if (isAddCookie) {
            request = new Request.Builder().url(url).addHeader("cookie", "PHPSESSID=").post(requestBodyPost).build();
        } else {
            request = new Request.Builder().url(url).post(requestBodyPost).build();
        }
        call(request, callBack, loadingDialog);
    }

    public void post(String url, RequestBody requestBodyPost, final RequestCallBack callBack, Dialog loadingDialog) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        MediaType requestType = MediaType.parse("application/json; charset=utf-8");
        Request request = null;
        request = new Request.Builder().url(url).post(requestBodyPost).build();
        call(request, callBack, loadingDialog);
    }
//    public void post(String url, RequestBody requestBodyPost, final RequestCallBack callBack, Dialog loadingDialog) {
//        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//        MediaType requestType
//                = MediaType.parse("application/json; charset=utf-8");
//        Request request = null;
//
////        if (params == null) {
////            request = new Request.Builder().url(url).build();
////        } else {
////            String content = "";
////            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
////            while (iterator.hasNext()) {
////                Map.Entry<String, String> item = iterator.next();
////                content += item.getKey() + "=" + item.getValue();
////                if (iterator.hasNext()) {
////                    content += "&";
////                }
////            }
////            RequestBody body = RequestBody.create(requestType, content);
////        if (isAddCookie) {
////            request = new Request.Builder().url(url)
////                    .addHeader("cookie", "user=" + UserRelated.user_token).post(requestBodyPost).build();
////        } else {
//        request = new Request.Builder().url(url).post(requestBodyPost).build();
////        }
//        call(request, callBack, loadingDialog);
//    }

    public void post2(String url, Map<String, String> map, final RequestCallBack callBack, Dialog loadingDialog) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        MediaType requestType
                = MediaType.parse("application/json; charset=utf-8");
        Request request = null;
        FormBody.Builder requestBodyPost = new FormBody.Builder();
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            requestBodyPost.add(key, value);
        }
//        if (params == null) {
//            request = new Request.Builder().url(url).build();
//        } else {
//            String content = "";
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> item = iterator.next();
//                content += item.getKey() + "=" + item.getValue();
//                if (iterator.hasNext()) {
//                    content += "&";
//                }
//            }
//            RequestBody body = RequestBody.create(requestType, content);
        request = new Request.Builder().url(url).post(requestBodyPost.build()).build();
//        }
        call(request, callBack, loadingDialog);
    }
//    /**
//     * 使用Post方式进行请求
//     *
//     * @param url           请求地址
//     * @param params        请求的参数
//     * @param callBack      请求的回调
//     * @param loadingDialog 等待框
//     */
//    public void post(String url, Map<String, String> params, final RequestCallBack callBack, Dialog loadingDialog) {
//        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//        MediaType requestType
//                = MediaType.parse("application/json; charset=utf-8");
//        Request request = null;
//        if (params == null) {
//            request = new Request.Builder().url(url).build();
//        } else {
//            String content = "";
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> item = iterator.next();
//                content += item.getKey() + "=" + item.getValue();
//                if (iterator.hasNext()) {
//                    content += "&";
//                }
//            }
//            RequestBody body = RequestBody.create(requestType, content);
//            request = new Request.Builder().url(url).post(body).build();
//        }
//        call(request, callBack, loadingDialog);
//    }

//    /**
//     * 使用Post方式进行请求
//     *
//     * @param url           请求地址
//     * @param json          请求的参数json格式
//     * @param callBack      请求的回调
//     * @param loadingDialog 等待框
//     */
//    public void post(String url, String json, final RequestCallBack callBack, Dialog loadingDialog, boolean isAddCookie) {
//        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//        MediaType requestType
//                = MediaType.parse("application/json; charset=utf-8");
//        Request request = null;
//        RequestBody body = RequestBody.create(requestType, json);
////        request = new Request.Builder().url(url).post(body).build();
//        if (isAddCookie) {
//            request = new Request.Builder().url(url)
//                    .addHeader("cookie", "user=" + UserRelated.user_token).post(body).build();
//        } else {
//            request = new Request.Builder().url(url).post(body).build();
//        }
//        call(request, callBack, loadingDialog);
//    }

//    /**
//     * 使用Post方式进行请求
//     *
//     * @param url           请求地址
//     * @param json          请求的参数json格式
//     * @param callBack      请求的回调
//     * @param loadingDialog 等待框
//     */
//    public void post(String url, String json, final RequestCallBack callBack, Dialog loadingDialog) {
//        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//        MediaType requestType
//                = MediaType.parse("application/json; charset=utf-8");
//        Request request = null;
//        RequestBody body = RequestBody.create(requestType, json);
//        request = new Request.Builder().url(url).post(body).build();
//        call(request, callBack, loadingDialog);
//    }

    private void call(final Request request, final RequestCallBack callBack, final Dialog loadingDialog) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                sendFailMessage(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                try {
                    String result = response.body().string().trim();
                    sendSuccessMessage(result, callBack);
                } catch (Exception e) {
                    sendFailMessage(response.request(), e, callBack);
                }
            }
        });
    }

    private void sendFailMessage(final Request request, final Exception e, final RequestCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.fail(request, e);
                }
            }
        });
    }

    private void sendSuccessMessage(final String result, final RequestCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.success(result);
                }
            }
        });
    }

}
