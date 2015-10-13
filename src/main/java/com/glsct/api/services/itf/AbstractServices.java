package com.glsct.api.services.itf;

import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServiceStatusCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
public class AbstractServices {

    public Map<String,Object> insertOK(){
            Map<String,Object> okMap = new HashMap<>();
        okMap.put(ResponseResult.Status_Code , ServiceStatusCode.Sucess);
        return okMap;
    }

    public Map<String,Object> insertOK(Integer rtnPk){
        Map<String,Object> okMap = insertOK();
        okMap.put(ResponseResult.Return_Pk, rtnPk.toString() );
        return okMap;
    }
}
