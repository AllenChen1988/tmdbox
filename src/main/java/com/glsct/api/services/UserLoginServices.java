package com.glsct.api.services;
import com.glsct.api.constant.dao.MapperReturnKey;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.constant.services.UserLoginType;
import com.glsct.api.except.GlsctException;
import com.glsct.api.services.itf.IServices;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/6.
 */

@Service(ServicesType.User_Login + ServicesType.SERVICES_SUFFIX)
public class UserLoginServices implements IServices{

    @Resource
    private SqlSessionTemplate sqlsession;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T,R> R service(T requestParms) throws Exception {
        Map<String,Object> params = (Map<String,Object>)requestParms;
        if(params.get(UserLoginType.REQUEST_PARAM_NAME).equals(UserLoginType.SELF_USER )){
            R r = sqlsession.selectOne("com.glsct.api.repository.mapper.UserMapper.findUserByUserNameAndPasswd",params );
            if(null != r ){
                return r;
            }
            throw new GlsctException(ServiceStatusCode.Login_Except);
        }else if (params.get(UserLoginType.REQUEST_PARAM_NAME).equals(UserLoginType.THIRD_PART_USER)){
            R r =  sqlsession.selectOne("com.glsct.api.repository.mapper.UserMapper.findUserByThirdPartUUId", params);
            if (null != r){
                return r ;
            }else{
                int new_user = sqlsession.insert("com.glsct.api.repository.mapper.UserMapper.registerUser",params );
                if (new_user > 0){
                    int rtn_pk = (int) params.get(MapperReturnKey.INSERT_RETURN_KEY);
                    params.put("user_id",rtn_pk);
                    int new_third_user = sqlsession.insert("com.glsct.api.repository.mapper.UserMapper.registerThirdPartUser",params );
                    r =  sqlsession.selectOne("com.glsct.api.repository.mapper.UserMapper.findUserByThirdPartUUId", params);
                    if (null != r){
                        return r;
                    }
                }
            }
            throw  new GlsctException(ServiceStatusCode.Login_Except);
        }else{
            throw new GlsctException(ServiceStatusCode.Params_Except);
        }
    }
}
