package com.glsct.api.services.itf;

/**
 * Created by Administrator on 2015/10/6.
 */
public interface IServices {
    <T,R> R service(T requestParms) throws Exception;
}
