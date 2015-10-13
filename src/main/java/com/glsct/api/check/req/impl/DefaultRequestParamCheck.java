package com.glsct.api.check.req.impl;

import com.glsct.api.check.req.RequestParamCheck;
import com.glsct.api.except.GlsctException;

/**
 * Created by Administrator on 2015/10/6.
 */
public class DefaultRequestParamCheck extends RequestParamCheck {
    @Override
    protected <T> T parse(T param) throws GlsctException {
        return param;
    }
}
