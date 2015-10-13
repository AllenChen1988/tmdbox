package com.glsct.api.controller;

import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.constant.url.RequestMapperURL;
import com.glsct.api.except.GlsctException;
import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/9/23.
 */
@RestController
public class UserController extends AbstractController {
    /**
     * 用户登录
     * @param param
     * @return
     */
    @RequestMapping(value = RequestMapperURL.USER_LOGIN ,method = RequestMethod.POST)
    public @ResponseBody Callable<Map<String,Object>> userLogin(@RequestBody Map<String,Object> param){
            return super.doFeatureRequestServices(ServicesType.User_Login,param);
    }

}
