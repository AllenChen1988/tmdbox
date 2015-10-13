import com.glsct.api.constant.PostType;
import com.glsct.api.constant.ThirdPartUserType;
import com.glsct.api.constant.services.UserLoginType;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/6.
 */
public class TmdTest {
    private final static String baseUrl = "http://localhost:8080/tmdbox";

    public static void main(String[] args) throws Exception{
        post_media_Test();
    }

    public static void post_media_Test() throws Exception{
        Map<String,Object> param = new HashMap<>();
        param.put("user_id","1");
        param.put("post_type", PostType.POSTS) ;
        param.put("content","this is test");
        List<String> medias = new ArrayList<>() ;
        medias.add("/res/upload/d6334ffb-c2bc-47a4-8fed-9b97cbdef391.png");
        param.put("media",medias);
        Gson g = new Gson();
        String j = g.toJson(param);
        String url = baseUrl + "/api/v1/posts" ;
        postTest(url, j);
    }


    public static void imgUpload(String url) throws Exception{

        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder meb =MultipartEntityBuilder.create();

        HttpEntity entity = meb.build();
    }




    public static void userLoginTest() throws Exception{
        Map<String,Object> param = new HashMap<>();
        param.put("user_name","miao");
        param.put("passwd","123") ;
        param.put(UserLoginType.REQUEST_PARAM_NAME,UserLoginType.THIRD_PART_USER);
        param.put("tpuid","oZXHSvkCQtHiFLMMKGJWD3EwOVTo");
        param.put("access_token","OezXcEiiBSKSxW0eoylIeB_HNFAh6KxpfUeByTv669iY6w4siLoAZb-pzXAwHk-C8C3cWnypVT8wiO8QJlVEYzUMhoWQz0jY4S8UEk32WR_KjejCzAHnxyhV6qbzydJs2A4n0Mi567ciQ8Lr3BaQiw");
        param.put("tptype", ThirdPartUserType.WECHAR);
        Gson g = new Gson();
        String j = g.toJson(param);
        String url = baseUrl + "/api/v1/login" ;
        postTest(url,j);
    }


    public static void postTest(String url,String json ) throws Exception{
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(json , ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        try(CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse resp = client.execute(post);
            InputStream in = resp.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufRead = new BufferedReader(inputStreamReader);
        ){
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ( null != (str = bufRead.readLine() )){
                sb.append(str);
            }
            System.out.println(sb);
        }
    }
}
