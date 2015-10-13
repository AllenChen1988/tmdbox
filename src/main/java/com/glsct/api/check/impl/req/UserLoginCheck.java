package com.glsct.api.check.impl.req;

import com.glsct.api.check.req.RequestParamCheck;
import com.glsct.api.constant.ThirdPartUserType;
import com.glsct.api.constant.UserStatus;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.constant.services.UserLoginType;
import com.glsct.api.constant.url.ThirdPartURL;
import com.glsct.api.except.GlsctException;
import com.glsct.utils.EncryptUtil;
import com.glsct.utils.HttpRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/10/7.
 */
@Component(ServicesType.User_Login + ServicesType.REQUEST_PARAM_SUFFIX)
public class UserLoginCheck extends RequestParamCheck {

    @Override
    protected <T> T parse(T param) throws GlsctException {
        Map<String,Object>  oldRequestMap = null;
        Map<String,Object> newRequestMap = null;
        if (null != param ){
            oldRequestMap = (Map<String, Object>) param;
            //用户登录类型
            if(oldRequestMap.containsKey(UserLoginType.REQUEST_PARAM_NAME)){
                String login_user_type = (String)oldRequestMap.get(UserLoginType.REQUEST_PARAM_NAME);
                //如果是第三方登录并且包含第三方登录类型
                if (login_user_type.equals(UserLoginType.THIRD_PART_USER) && oldRequestMap.containsKey(ThirdPartUserType.REQUEST_PARAM_NAME)){
                    String tp_user_type = (String) oldRequestMap.get(ThirdPartUserType.REQUEST_PARAM_NAME);
                    switch (tp_user_type){
                        case ThirdPartUserType.WECHAR:
                            newRequestMap = getWeCharInfo(oldRequestMap);
                            break;
                        case ThirdPartUserType.WEIBO:
                            break;
                        case ThirdPartUserType.QQ:
                            break;
                        default:
                            break;
                    }
                    //
                    String un = ((Long)(System.currentTimeMillis()/1000)).toString();
                    String passwd =  ((Long)System.currentTimeMillis()).toString();
                    newRequestMap.put("user_name",un);
                    newRequestMap.put("passwd",EncryptUtil.encryptString(passwd));
                }else {
                    Object passwd = oldRequestMap.get("passwd");
                    if (null != passwd){
                        oldRequestMap.put("passwd", EncryptUtil.encryptString((String) passwd));
                        newRequestMap = oldRequestMap;
                    }
                }
            }
        }

        newRequestMap.put("brithday",null);
        newRequestMap.put("status", UserStatus.NORMAL);
        newRequestMap.put("create_time",new Date());
        newRequestMap.put("type",newRequestMap.get("tptype"));

        return (T)newRequestMap;
    }

    private Map<String,String> warpWecharParam(final Map<String,Object> params){
        Map<String,String> wechar = new HashMap<>();
        Object access_tokenObj = params.get("access_token");
        Object openid = params.get("tpuid");
        if (null != access_tokenObj && null != openid){
            wechar.put("access_token",(String)access_tokenObj);
            wechar.put("openid" , (String)openid);
        }
        return wechar;
    }


    private Map<String,Object> getWeCharInfo(final Map<String, Object> params){
        Map<String, Object> inculde_wechar = new HashMap<>();
        inculde_wechar.putAll(params);
        Map<String, String> wechar = warpWecharParam(params);
        Map<String,Object> check_login =  HttpRequestUtil.doHttpGet(ThirdPartURL.WeChar_Check_Login,wechar);
        if (null != check_login && check_login.containsKey("errcode")){
            int errorcode = ((Double)check_login.get("errcode")).intValue();
            if (errorcode == 0){
                Map<String,Object> userInfo  =  HttpRequestUtil.doHttpGet(ThirdPartURL.WeChar_UserInfo, wechar);
                if (null != userInfo && !userInfo.isEmpty()){
                    inculde_wechar.put("tpuid",userInfo.get("openid"));
                    inculde_wechar.put("nick_name",userInfo.get("nickname"));
                    inculde_wechar.put("sex",((Double)userInfo.get("sex")).intValue());
                    inculde_wechar.put("address", userInfo.get("city"));
                    inculde_wechar.put("photo" , userInfo.get("headimgurl"));
                }
            }
        }
        return inculde_wechar;
    }

}
