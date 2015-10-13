package com.glsct.api.check.impl.req;

import com.glsct.api.check.req.RequestParamCheck;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.except.GlsctException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2015/10/8.
 */
@Component(ServicesType.User_Posts_Publish + ServicesType.REQUEST_PARAM_SUFFIX)
public class PostPublishCheck extends RequestParamCheck {
    @Override
    protected <T> T parse(T param) throws GlsctException {
        if (null != param) {
            Date create_time = new Date();
            Map<String, Object> newRequestParam = new HashMap<>();
            Map<String, Object> oldRequestParam = (Map<String,Object>)param;
            newRequestParam.put("user_id", oldRequestParam.get("user_id"));
            newRequestParam.put("content", oldRequestParam.get("content"));
            newRequestParam.put("create_time", create_time);
            newRequestParam.put("type", oldRequestParam.get("post_type"));

            Object listObj = oldRequestParam.get("media");
            if (null != listObj) {
                List<String> medias = (List<String>) listObj;
                List<Map<String, Object>> post_medias = new ArrayList<>();
                Map<String, Object> post_media = null;
                for (int i = 0; i < medias.size(); i++) {
                    post_media = new HashMap<>();
                    post_media.put("media_url", medias.get(i));
                    post_media.put("order_no", i);
                    post_media.put("create_time", create_time);
                    post_medias.add(post_media);
                }
                newRequestParam.put("post_media", post_medias);
            }
            return (T) newRequestParam;
        }
        return param;
    }
}
