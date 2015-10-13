package com.glsct.api.services;

import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.services.itf.IServices;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/10/7.
 */
public class PostsDetialQueryServices implements IServices {
    private final static Logger log = Logger.getLogger(PostsDetialQueryServices.class);
    @Override
    public <T,R> R service(T requestParms) throws Exception {
        return  null;
    }
}
