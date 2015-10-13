package com.glsct.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2015/10/7.
 */
public class HttpRequestUtil {

    public static Map<String,Object> doHttpGet(String url,Map<String,String> params){
        StringBuilder sb = new StringBuilder();
        if (null != params && !params.isEmpty()){
            sb.append("?");
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry<String, String> entry = iter.next();
                if (sb.length() > 1){
                    sb.append("&");
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
        }
        String respStr = null;

        try {
            respStr = doRequest(getHttpGet(url+sb.toString())) ;
        } catch (IOException e) {
            return getErrorMap();
        }

        if (!StringUtils.isEmpty(respStr)){
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.disableInnerClassSerialization().disableHtmlEscaping().enableComplexMapKeySerialization().create();
            Map<String,Object> result = g.fromJson(respStr,new TypeToken<Map<String,Object>>(){}.getType());
            return result;
        }
        return getErrorMap();
    }

    public static Map<String,Object> getErrorMap(){
        Map<String,Object > map = new HashMap<>();
        map.put("errcode","-1");
        return map;
    }


    public static HttpRequestBase getHttpGet(String url){
        HttpGet get = new HttpGet(url);
        return get;
    }


    public static String doRequest(HttpRequestBase req) throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault();
                    CloseableHttpResponse resp = client.execute(req);
                    InputStream in = resp.getEntity().getContent();
                    InputStreamReader inReader = new InputStreamReader(in);
                    BufferedReader bufRead = new BufferedReader(inReader);){
            String str = null;
            StringBuilder sb = new StringBuilder();
            while (null != (str = bufRead.readLine() )){
                sb.append(str);
            }
            return sb.toString();
        }
    }





}
