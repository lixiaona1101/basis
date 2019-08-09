package com.example.lixiaona.study.utils.net;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.lixiaona.study.R;
import com.example.lixiaona.study.utils.CollectionUtils;
import com.example.lixiaona.study.utils.NumberUtils;
import com.example.lixiaona.study.utils.StringUtils;
import com.example.lixiaona.study.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lixiaona.study.Config.userId;
import static com.example.lixiaona.study.Config.userToken;

/**
 * OkHttp网络连接封装工具类  解析用的是Gson 记得添加Gson依赖 或者jar包
 */
public class OkHttpUtils {
    //格式
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded");//mdiatype 这个需要和服务端保持一致


    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }

    //Code码
    public static final int CODE_SUCCESS = 0;                //成功
    public static final int CODE_DEAL_WITH_FAILURE = 410;    //处理失败
    public static final int CODE_BUSINESS_FAILURE = 402;     //业务失败
    public static final int CODE_NO_NET = 404;               //请求失败
    public static final int CODE_TOKEN_TIMEOUT = 501;        //请求超时
    public static final int CODE_SERVICE_ERROR = 500;        //服务器开小差了
    public static final int CODE_TOKEN_FAILURE = -1;         //登录失效
    public static final int CODE_TOKEN_CANCEL = 411;         //取消请求


    //存储post请求
    private static ArrayList<String> urls = new ArrayList<>();

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    /**
     * 初始化OkHttp
     */
    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        mDelivery = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取实列
     */
    private synchronized static OkHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }


    /**********************************************************************************************************
     *******************************************      get请求       ********************************************
     *  @param isToast      是否吐司
     *  @param url          请求网址
     *  @param paramObjects 请求参数
     *  @param callback     回调
     * */
    public static Call get(boolean isToast, boolean tokenFailurLogin, String url, LinkedHashMap<String, Object> paramObjects, ResultCallback<String> callback) {
        return getInstance().getRequest(isToast, tokenFailurLogin, url, paramObjects, callback);
    }

    public static Call get(boolean isToast, String url, LinkedHashMap<String, Object> paramObjects, ResultCallback<String> callback) {
        return getInstance().getRequest(isToast, true, url, paramObjects, callback);
    }

    public static Call get(String url, LinkedHashMap<String, Object> paramObjects, ResultCallback<String> callback) {
        return getInstance().getRequest(false, true, url, paramObjects, callback);
    }

    private Call getRequest(boolean isToast, boolean tokenFailurLogin, String url, LinkedHashMap<String, Object> params, final ResultCallback<String> callback) {
        if (userId != -1) {
            if (params == null)
                params = new LinkedHashMap<>();
            if (!params.containsKey("userId"))
                params.put("userId", userId);
            if (!params.containsKey("accessToken"))
                params.put("accessToken", userToken);
        }

        //判断是否有参数
        if (!CollectionUtils.isEmpty(params)) {
            StringBuilder param = new StringBuilder();
            //拼接参数
            int i = 0;
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (i == 0) {   //如果没有拼接?  则加上?
                    if (!url.contains("?"))
                        param.append("?");
                } else {        //如果不是第一个参数，则需要加&
                    param.append("&");
                }
                param.append(key).append("=").append(val);
                i++;
            }
            url = url + param.toString();
        }

        //请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        return deliveryResult(isToast, tokenFailurLogin, url, "Get: " + url, callback, request);
    }

    /**********************************************************************************************************
     *******************************************      post 表单请求       ***************************************
     ***********************************************************************************************************/
    public static void post(boolean isToast, String url, LinkedHashMap<String, Object> params, final ResultCallback callback) {
        getInstance().postRequest(isToast, url, params, callback);
    }

    public static void post(String url, LinkedHashMap<String, Object> params, final ResultCallback callback) {
        getInstance().postRequest(false, url, params, callback);
    }

    private Call postRequest(boolean isToast, String url, LinkedHashMap<String, Object> params, final ResultCallback<String> callback) {
        if (urls.contains(url)) {
            ToastUtils.show(R.string.frequent);
            return null;
        }


        if (userId != -1) {
            if (params == null)
                params = new LinkedHashMap<>();
            if (!params.containsKey("userId"))
                params.put("userId", userId);
            if (!params.containsKey("accessToken"))
                params.put("accessToken", userToken);
        }

//        StringBuilder sb = new StringBuilder();
//        //请求体 参数
//
//        if (!CollectionUtils.isEmpty(params)) {
//            int i = 0;
//            for (Object o : params.entrySet()) {
//                Map.Entry entry = (Map.Entry) o;
//                String key = (String) entry.getKey();
//                Object value = entry.getValue();
//
//                if (i == 0) {
//                    sb.append("{");
//                }
//
//                sb.append("\"").append(key).append("\":");
//                if (value instanceof String) {
//                    if (((String) value).endsWith("}") && ((String) value).startsWith("{")) {
//                        sb.append(value);
//                    } else if (((String) value).endsWith("]") && ((String) value).startsWith("[")) {
//                        sb.append(value);
//                    } else {
//                        sb.append("\"").append(value).append("\"");
//                    }
//                } else {
//                    sb.append(value);
//                }
//
//                if (i == params.size() - 1) {
//                    sb.append("}");
//                } else {
//                    sb.append(",");
//                }
//
//                i++;
//            }
//        }


        //请求体
        FormBody.Builder builder = new FormBody.Builder();

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (!CollectionUtils.isEmpty(params)) {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                String val = String.valueOf(entry.getValue());

                builder.add(key, val);

//                builder.addEncoded()

                sb.append("\"").append(key).append("\"").append(":").append("\"").append(val).append("\"").append(",");
            }
        }

        RequestBody requestBody = builder.build();
        String string = sb.append("}").toString();

//        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, sb.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        String newUrl = "Post: " + url + "\nParams: " + string;
        return deliveryResult(isToast, true, url, newUrl, callback, request);
    }

    /**********************************************************************************************************
     *******************************************      post 表单形式 混合参数和文件       *************************
     *  @param isToast      是否吐司
     *  @param url          请求网址
     *  @param params       请求参数
     *  @param fileKey      文件key
     *  @param fileList     文件路径
     *  @param callback     回调
     *  @param progressListener     进度回调
     * */
    public static Call postFile(boolean isToast, String url, LinkedHashMap<String, String> params, List<String> fileKey, List<String> fileList, final ResultCallback<String> callback, ProgressListener progressListener) {
        return getInstance().postRequest(isToast, url, params, fileKey, fileList, callback, progressListener);
    }

    public static Call postFile(String url, LinkedHashMap<String, String> params, List<String> fileKey, List<String> fileList, final ResultCallback<String> callback, ProgressListener progressListener) {
        return getInstance().postRequest(false, url, params, fileKey, fileList, callback, progressListener);
    }

    private Call postRequest(boolean isToast, String url, LinkedHashMap<String, String> params, List<String> fileKey, List<String> fileNames,
                             final ResultCallback<String> callback, ProgressListener progressListener) {
        if (urls.contains(url)) {
            ToastUtils.show(R.string.frequent);
            return null;
        }
        urls.add(url);

        if (userId != -1) {
            if (params == null)
                params = new LinkedHashMap<>();
            if (!params.containsKey("userId"))
                params.put("userId", userId + "");
            if (!params.containsKey("accessToken"))
                params.put("accessToken", userToken);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        //请求体
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!CollectionUtils.isEmpty(params)) {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                builder.addFormDataPart(key, val);
                sb.append("\"").append(key).append("\"").append(":").append("\"").append(val).append("\"").append(",");
            }
        } else {
            builder.addFormDataPart("", "");
        }

        if (fileNames != null) {
            if (fileNames.size() > 0) {
                for (int i = 0; i < fileNames.size(); i++) { //对文件进行遍历
                    File file = new File(fileNames.get(i)); //生成文件
                    if (file.exists()) {
                        //根据文件的后缀名，获得文件类型
                        String fileType = getMimeType(file.getName());
                        builder.addFormDataPart( //给Builder添加上传的文件
                                fileKey.get(i),  //请求的名字
                                file.getName(), //文件的文字，服务器端用来解析的
                                RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
                        );

                        sb.append("\"").append(fileKey.get(i)).append("\"").append(":").append("\"").append(file.getPath()).append("\"").append(",");
                    }

                    Logger.i(file + "\t是否存在" + file.exists());
                }
            }
        }
        RequestBody requestBody = builder.build();
        String string = sb.append("}").toString();
        String newUrl = "Post File: " + url + "\nParams: " + string;

        //请求
        Request request = new Request.Builder()
                .url(url)
                .post(ProgressHelper.addProgressRequestListener(
                        requestBody,
                        progressListener))
                .build();
        return deliveryResult(isToast, true, url, newUrl, callback, request);
    }

    /**********************************************************************************************************
     *******************************************      post Json请求、拼到body里       ***************************
     *  @param isToast      是否吐司
     *  @param url          请求网址
     *  @param params       请求参数
     *  @param callback     回调
     * */
    public static Call postJson(boolean isToast, String url, LinkedHashMap<String, Object> params, final ResultCallback<String> callback) {
        return getInstance().postRequestObject(isToast, url, callback, params);
    }

    public static Call postJson(String url, LinkedHashMap<String, Object> params, final ResultCallback<String> callback) {
        return getInstance().postRequestObject(false, url, callback, params);
    }

    private Call postRequestObject(boolean isToast, String url, final ResultCallback<String> callback, LinkedHashMap<String, Object> params) {
        if (urls.contains(url)) {
            ToastUtils.show(R.string.frequent);
            return null;
        }
        urls.add(url);

        if (userId != -1) {
            if (params == null)
                params = new LinkedHashMap<>();
            if (!params.containsKey("userId"))
                params.put("userId", userId);
            if (!params.containsKey("accessToken"))
                params.put("accessToken", userToken);
        }

        StringBuilder sb = new StringBuilder();
        //请求体 参数

        if (!CollectionUtils.isEmpty(params)) {
            int i = 0;
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                if (i == 0) {
                    sb.append("{");
                }

                sb.append("\"").append(key).append("\":");
                if (value instanceof String) {
                    if (((String) value).endsWith("}") && ((String) value).startsWith("{")) {
                        sb.append(value);
                    } else if (((String) value).endsWith("]") && ((String) value).startsWith("[")) {
                        sb.append(value);
                    } else {
                        sb.append("\"").append(value).append("\"");
                    }
                } else {
                    sb.append(value);
                }

                if (i == params.size() - 1) {
                    sb.append("}");
                } else {
                    sb.append(",");
                }

                i++;
            }
        }

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, sb.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        String newUrl = "Post Json: " + url + "\nParams: " + sb.toString();
        return deliveryResult(isToast, true, url, newUrl, callback, request);
    }


    /**********************************************************************************************************
     *******************************************      处理结果       ********************************************
     *  @param isToast      是否吐司
     *  @param url          请求网址,   用于移除存储post请求列表的url
     *  @param params       请求参数和网址，用于请求结束时打印
     *  @param callback     回调
     *  @param request      请求对象
     * */
    private Call deliveryResult(boolean isToast, boolean tokenFailureLogin, final String url, final String params, final ResultCallback<String> callback, final Request request) {

        if (mOkHttpClient == null) {
            NullPointerException e = new NullPointerException("mOkHttpClient 为空");
            sendFailCallback(callback, e, CODE_DEAL_WITH_FAILURE);
            urls.remove(url);
            return null;
        }
        String activityGPS = wrapperContent() + "\n\n";

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                urls.remove(url);
                Logger.i(activityGPS + params + "\n" + e.getMessage());
                sendFailCallback(isToast, callback, new Exception("请求失败，请稍后再试…"), CODE_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //原Code
                Integer code = response.code();
                String body = response.body().string();
                String mBody = body;
                String msg = "";
                //原Body
                try {
                    JSONObject object1 = JSON.parseObject(body);

                    if (object1.containsKey("code")) {
                        code = NumberUtils.toInt(object1.getString("code"), -1);
                        body = object1.getString("data");
                        msg = object1.getString("msg");

                        if (code != CODE_SUCCESS && isToast) {
                            if (!StringUtils.isEmpty(msg))
                                ToastUtils.show(msg);
                        }
                    }

                    Logger.i(activityGPS + params +
                            "\nCode: " + code +
                            "\nmsg: " + msg +
                            "\nBody: " + body);

//                    if (url.contains(url_userReg) ||
//                            url.contains(url_loginSubmit)) {
//                        if (!StringUtils.isEmpty(body) && body.startsWith("{")) {
//                            JSONObject jsonObject = JSON.parseObject(body);
//                            if (jsonObject.containsKey("userId")) {
//                                userId = jsonObject.getInteger("userId");
//                            }
//                        }
//                    }


                    if (code == CODE_SUCCESS)
                        sendSuccessCallBack(callback, body, code);
                    else
                        sendFailCallback(isToast, callback, new Exception(msg), code);
                } catch (final Exception e) {
                    Logger.i(activityGPS + params +
                            "\nmBody:" + mBody +
                            "\nCode: " + code +
                            "\nBody: " + body);
                    sendFailCallback(isToast, callback, e, CODE_DEAL_WITH_FAILURE);
                } finally {
                    urls.remove(url);
                }
            }
        });
        return call;
    }


    /**********************************************************************************************************
     *******************************************      处理结果回调       ********************************************
     ***********************************************************************************************************/
    //请求失败
    private void sendFailCallback(final ResultCallback<String> callback, final Exception e, final int code) {
        sendFailCallback(false, callback, e, code);
    }

    //请求成功
    private void sendSuccessCallBack(final ResultCallback<String> callback, final String obj, final int code) {
        mDelivery.post(() -> {
            try {
                callback.onSuccess(code, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    //请求失败
    private void sendFailCallback(boolean isToast, final ResultCallback callback, final Exception e, final int code) {


        mDelivery.post(() -> {
            int mCode = code;
            if (e.getMessage() == null) {                       //请求超时
                mCode = CODE_TOKEN_TIMEOUT;
                if (isToast) ToastUtils.show(R.string.request_timeout);
            } else if ("Canceled Socket closed".contains(e.getMessage())) {    //取消请求
                mCode = CODE_TOKEN_CANCEL;
                if (isToast) ToastUtils.show(R.string.cancel_request);
            } else if ("timeout".contains(e.getMessage())) {            //请求超时
                mCode = CODE_TOKEN_TIMEOUT;
                if (isToast) ToastUtils.show(R.string.request_timeout);
            } else {                                                    //请求失败
                if (isToast) {
                    if (code == CODE_DEAL_WITH_FAILURE)             //数据异常
                        ToastUtils.show(R.string.abnormal_data);
                    else
                        ToastUtils.show(e.getMessage());
                }
            }

            try {
                callback.onFailure(mCode, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }


    /**
     * http请求回调类,回调方法在UI线程中执行
     */
    public static abstract class ResultCallback<T> {

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(int code, String response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(int code, Exception e);
    }

    /**
     * 定位 Ok请求 在Activity的位置
     */
    private String wrapperContent() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[6];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + ".java";
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + ".java";
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        return "(" + className + ":" + lineNumber + ") ☞ " + methodNameShort;
    }

}
