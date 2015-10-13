package com.glsct.api.check.req;

import com.glsct.api.except.GlsctException;

public abstract class RequestParamCheck {

    public <T> T check(T param) throws GlsctException {
        return this.<T>parse(param);
    }

    protected abstract  <T> T parse(T param) throws GlsctException;

}
