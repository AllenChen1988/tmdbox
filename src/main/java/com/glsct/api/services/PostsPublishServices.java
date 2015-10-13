package com.glsct.api.services;

import com.glsct.api.constant.dao.MapperReturnKey;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.except.GlsctException;
import com.glsct.api.services.itf.AbstractServices;
import com.glsct.api.services.itf.IServices;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
@Service(ServicesType.User_Posts_Publish + ServicesType.SERVICES_SUFFIX)
public class PostsPublishServices extends AbstractServices implements IServices {

    @Resource
    private SqlSessionTemplate sqlsession;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T,R> R service(T requestParms) throws Exception {
        if (null != requestParms ){
            Map<String,Object> oldRequestParams = (Map<String,Object>)requestParms ;
            int rtnCnt = sqlsession.insert("com.glsct.api.repository.mapper.PostsMapper.postPublish" ,oldRequestParams);
            if (rtnCnt > 0){
                int rtnPk = (int) oldRequestParams.get(MapperReturnKey.INSERT_RETURN_KEY);
                Object obj = oldRequestParams.get("post_media");
                if (null != obj){
                    List<Map<String,Object>> post_medias = (List<Map<String, Object>>) obj;
                    for (Map<String,Object> pm : post_medias ){
                        pm.put("post_id", rtnPk);
                        int rtnCnt_media = sqlsession.insert("com.glsct.api.repository.mapper.PostsMapper.postMediaPublish",pm);
                    }
                }
                return (R) super.insertOK(rtnPk);
            }
        }
        throw  new GlsctException(ServiceStatusCode.Params_Except);
    }
}
