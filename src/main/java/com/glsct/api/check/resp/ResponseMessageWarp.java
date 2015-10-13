package com.glsct.api.check.resp;

/**
 * Created by Administrator on 2015/9/30.
 */
public abstract class ResponseMessageWarp {



    /**
     * �������ذ�װ
     * @param parms
     * @param <T>
     * @return
     */
    public abstract <T> T warp(final T parms) throws Exception;



}
