package com.glsct.api.check.impl.req;

import com.glsct.api.check.req.RequestParamCheck;
import com.glsct.api.constant.dao.PageQuery;
import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.except.GlsctException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2015/10/8.
 */
@Component(ServicesType.User_Posts_List_Query + ServicesType.REQUEST_PARAM_SUFFIX)
public class PostListQueryCheck extends RequestParamCheck {
    @Override
    protected <T> T parse(T param) throws GlsctException {
        if (null != param) {
            Map<String, Object> oldRequestParam = (Map<String,Object>)param;
            oldRequestParam.put(ResponseResult.Page_Size, PageQuery.Default_Page_Size);
            return (T) oldRequestParam;
        }
        return param;
    }
}
