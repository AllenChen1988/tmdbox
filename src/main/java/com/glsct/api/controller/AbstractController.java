package com.glsct.api.controller;

import com.glsct.api.check.req.impl.DefaultRequestParamCheck;
import com.glsct.api.check.resp.impl.DefaultResponseMessageWarp;
import com.glsct.api.check.req.RequestParamCheck;
import com.glsct.api.check.resp.ResponseMessageWarp;
import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.except.GlsctException;
import com.glsct.api.services.itf.IServices;
import com.glsct.utils.ApplicationBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/10/6.
 */
public abstract class AbstractController {

    private final static Logger log = Logger.getLogger(AbstractController.class);

    protected  Callable<Map<String, Object>> doFeatureRequestServices(final String serviceType, final Map<String, Object> requestParms){
        return new Callable<Map<String, Object>>() {
            @Override
            public Map<String, Object> call() throws Exception {
//                long start = System.currentTimeMillis();
                try{
                    if(log.isDebugEnabled()){
                        log.debug("====================执行服务:"+serviceType + "====================");
                    }
                    return AbstractController.this.requestServices(serviceType , requestParms);
                }catch (final Exception  exp ){
                    if(log.isDebugEnabled()){
                        log.debug("====================异步服务请求失败: ServiceType"+ serviceType + "  ====================");
                    }
                    return  AbstractController.this.warpExecption(exp);
                }finally {
//                    long end = System.currentTimeMillis();
//                    System.out.print("===================="+ (end - start) + "====================");
                }
            }
        };
    }

    protected Map<String, Object> requestServices(final String serviceType,final Map<String, Object> requestParms) throws Exception {
        IServices services = null;
        Object serviceObj = ApplicationBeanUtil.getBean(serviceType + ServicesType.SERVICES_SUFFIX);
        if (null == serviceObj) {
            if (log.isDebugEnabled()) {
                log.debug("====================获取服务类失败,清检查是否注入spring context====================");
            }
            throw new GlsctException(ServiceStatusCode.Server_Except);
        } else {
            services = (IServices) serviceObj;
        }

        RequestParamCheck reqCheck = null;
        Object reqCheckObj = ApplicationBeanUtil.getBean(serviceType + ServicesType.REQUEST_PARAM_SUFFIX);
        if (null == reqCheckObj) {
            if (log.isDebugEnabled()) {
                log.debug("====================获取参数检查类失败,将使用默认的参数检查类====================");
            }
            reqCheck = new DefaultRequestParamCheck();
        } else {
            reqCheck = (RequestParamCheck) reqCheckObj;
        }
        ResponseMessageWarp respWarp = null;
        Object respWarpObj = ApplicationBeanUtil.getBean(serviceType + ServicesType.RESPONSE_WARP_SUFFIX);
        if (null == respWarpObj) {
            if(log.isDebugEnabled()){
                log.debug("====================获取Response包装类失败,将使用默认的类====================");
            }
            respWarp = new DefaultResponseMessageWarp();
        } else {
            respWarp = (ResponseMessageWarp) respWarpObj;
        }
        Map<String, Object> checkedParms = reqCheck.check(requestParms);
        Map<String, Object> result = null;
        try{
            result = services.service(checkedParms);
        }catch (final Throwable t){
            if(log.isDebugEnabled()){
                log.debug("====================执行服务类失败====================");
            }
            t.printStackTrace();
            if (t instanceof GlsctException){
                return this.warpExecption((GlsctException)t);
            }

            throw new GlsctException(ServiceStatusCode.Server_Except);
        }
        return respWarp.warp(result);
    }


    public Map<String, Object> warpExecption(final Exception exc) {
        if(exc instanceof  GlsctException){
            return this.createErrorMessage((GlsctException)exc);
        }else{
            return createErrorMessage(exc);
        }
    }

    public Callable<Map<String, Object>> warpFeatureExecption(final Exception exc) {
        return  new Callable<Map<String, Object>>() {
            @Override
            public Map<String, Object> call() throws Exception {
                    return AbstractController.this.warpExecption(exc);
            }
        };
    }


    private Map<String,Object> createErrorMessage(final Exception exc){
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put(ResponseResult.Status_Code, ServiceStatusCode.Unknow_Except);
        return errorInfo;
    }

    private Map<String, Object> createErrorMessage(final GlsctException exc) {
        String msg = StringUtils.EMPTY;
            switch (exc.getErrorCode()) {
                case ServiceStatusCode.Busi_Except:
                    break;
                case ServiceStatusCode.Params_Except:
                    break;
                case ServiceStatusCode.Server_Except:
                    break;
                default:
                    break;
            }
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put(ResponseResult.Status_Code, exc.getErrorCode());
            return errorInfo;
    }


}
