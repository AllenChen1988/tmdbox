package com.glsct.api.check.resp.impl;

import com.glsct.api.check.resp.ResponseMessageWarp;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.except.GlsctException;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

/**
 * Created by Administrator on 2015/10/6.
 */

public class DefaultResponseMessageWarp extends ResponseMessageWarp {
    private static final Logger log = Logger.getLogger(DefaultResponseMessageWarp.class);

    @Override
    public <T> T warp(T parms) throws Exception{
        if (null == parms){
            if(log.isDebugEnabled()){
                log.debug("====================返回数据为空，包装类抛出异常====================");
            }
            throw new GlsctException(ServiceStatusCode.Unknow_Except);
        }
        return parms;
    }
}
