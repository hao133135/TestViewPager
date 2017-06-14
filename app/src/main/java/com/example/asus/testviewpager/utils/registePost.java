package com.example.asus.testviewpager.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/6/14.
 */

public class registePost {
    public boolean Post(String phone, String password, int role, String verCode, String address) throws Exception {
        phone = URLEncoder.encode(phone);// 中文数据需要经过URL编码
        password = URLEncoder.encode(password);
        verCode = URLEncoder.encode(verCode);
        String params = "phone=" + phone + "&password=" + password+"&role="+role+"&verCode="+verCode;
        byte[] data = params.getBytes();

        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
//这是请求方式为POST
        conn.setRequestMethod("POST");
//设置post请求必要的请求头
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
        conn.setRequestProperty("Content-Length", data.length + "");// 注意是字节长度, 不是字符长度

        conn.setDoOutput(true);// 准备写出
        conn.getOutputStream().write(data);// 写出数据
        return conn.getResponseCode() == 200;
    }
}
