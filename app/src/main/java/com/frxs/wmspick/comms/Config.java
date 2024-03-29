package com.frxs.wmspick.comms;

/**
 * Created by ewu on 2016/3/23.
 */
public class Config {
    public static final String DB_NAME = "dbname.db";

    public static final String PREFS_NAME = "MyFrefsFile";

    public static final String KEY_USER = "key_user";

    public static final String KEY_HIST = "key_history"; // 商品搜索历史

    public static final String SEARCH_PREFS_NAME = "SearchPrefsFile";

    public static final String REFRESH_WAIT_RECEIVED = "refresh_wait_received";// 是否刷新等待收货

    public static final String KEY_DEVICE_ID = "key_device_id";//设备id

    // 远程服务器网络 (0:线上环境、1:测试环境、2：演示环境)
    public static int networkEnv = 3;

    public static final int TYPE_BASE = 0;

    public static final int TYPE_UPDATE = 1;

    public static String getBaseUrl(int typeUrl) {
        return getBaseUrl(typeUrl, networkEnv);
    }

    public static String getBaseUrl(int typeUrl, int networkEnv) {
        String BASE_URL = "";
        if (networkEnv == 0) {
            if (typeUrl == TYPE_BASE) {
                BASE_URL = "http://app.f6.frxs.cn/api/";
            } else {
                BASE_URL = "http://app.f6.frxs.cn/api/";
            }
        } else if (networkEnv == 1) {
            if (typeUrl == TYPE_BASE) {
                BASE_URL = "http://192.168.6.157:8678/api/";//http://192.168.6.157:8678/api/   http://192.168.8.123:8086/api/
            } else {
                BASE_URL = "http://192.168.6.157:8678/api/";
            }
        } else if (networkEnv == 2) {
            if (typeUrl == TYPE_BASE) {
                BASE_URL = "http://yfbapi_wh.erp2.frxs.com/";// 预发布环境
            } else {
                BASE_URL = "http://yfbapi_wh.erp2.frxs.com/api/";
            }
        } else if (networkEnv == 3) {
            if (typeUrl == TYPE_BASE) {
                //BASE_URL = "http://192.168.8.246:8080/";// 演示环境
                //BASE_URL = "http://192.168.8.210:8099/";
                //BASE_URL = "http://192.168.8.63:8086/";
                //BASE_URL = "http://192.168.8.142:8090/";
                //BASE_URL = "http://192.168.8.156:8088/";//DDY
                BASE_URL = "http://192.168.8.215:8444/api/";//gzj
//                BASE_URL = "http://192.168.8.63:5080/api/"; //DL
//                BASE_URL = "http://192.168.8.123:8086/api/";//txh
                BASE_URL = "http://192.168.8.117:8089/api/";//tf
            } else {
                BASE_URL = "http://b2btest.frxs.cn/api/";
            }
        } else {
            if (typeUrl == TYPE_BASE) {
                BASE_URL = "http://api_wh.erp2.frxs.com/";
            } else {
                BASE_URL = "http://orderapi.erp2.frxs.com/api/";
            }
        }

        return BASE_URL;
    }

    public static String getSubimgUrl(){
        String SUBIMG_SERVER = "";// 获取当前图片上传地址
        if (networkEnv > 0){
            SUBIMG_SERVER = "http://itestimage.frxs.cn/api/";//测试
            //SUBIMG_SERVER = "http://192.168.8.142:8082/api/";//集成环境
            //SUBIMG_SERVER="http://192.168.8.66/Frxs.External.ImageAPI/api/";  //CR
        } else {
            SUBIMG_SERVER = "http://imagesup.erp2.frxs.com/api/";
        }

        return SUBIMG_SERVER;
    }
}