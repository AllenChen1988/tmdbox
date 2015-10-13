package com.glsct.api.except;

/**
 * Created by Administrator on 2015/9/30.
 */
public class GlsctException extends Exception {
    private final int errorCode ;

    public GlsctException(int status){
        this.errorCode = status;
    }

    public int getErrorCode(){
        return this.errorCode;
    }

}
