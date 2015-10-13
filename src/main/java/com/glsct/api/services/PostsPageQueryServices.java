package com.glsct.api.services;

import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.constant.url.RequestMapperParam;
import com.glsct.api.except.GlsctException;
import com.glsct.api.services.itf.AbstractServices;
import com.glsct.api.services.itf.IServices;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/7.
 */
@Service(ServicesType.User_Posts_List_Query + ServicesType.SERVICES_SUFFIX)
public class PostsPageQueryServices extends AbstractServices implements IServices {
    private final static Logger log = Logger.getLogger(PostsPageQueryServices.class);

    @Resource
    private SqlSessionTemplate sqlsession;


    @Override
    @Transactional(propagation = Propagation.REQUIRED ,readOnly = true)
    public <T,R> R service(T requestParms) throws Exception {
        if (log.isDebugEnabled()){
            log.debug("Start Services ^_^");
        }
        if (null != requestParms){
            return (R) this.queryPostByPage(requestParms);
        }
         throw new GlsctException(ServiceStatusCode.Params_Except);
    }


    public <T> Map<String,Object> queryPostByPage(T requestParms) throws Exception{
        Map<String,Object> oldParam =(Map<String,Object>)requestParms;
        Map<String,Object> pageInfo = this.computePageInfo(oldParam);
        pageInfo.put(RequestMapperParam.Posts_Type,oldParam.get(RequestMapperParam.Posts_Type));
        List<Map<String,Object>> list_posts  = this.queryPostData(pageInfo);

        List<Map<String,Object>> return_datas = new ArrayList<>();
        Map<String,Object> return_recd = null;

        for (Map<String,Object> posts : list_posts ){
            return_recd = new HashMap<>();
            return_recd.putAll(posts);
            int post_id = (Integer) posts.get("id");
            int user_id = (Integer) posts.get("user_id") ;
            return_recd.put("media", this.queryPostMediaByPostId(post_id));
            return_recd.put("user", this.querySimpleUserInfoById(user_id));
            HashMap<String, Object> isoptMap = new HashMap<>();
            isoptMap.put("post_id",post_id);
            isoptMap.put("user_id",user_id);
            return_recd.put("opt",this.queryUserIsPlusPost(isoptMap));
            return_recd.put("post_type",posts.get("type"));

            return_recd.remove("user_id");
            return_recd.remove("type");
            return_datas.add(return_recd);
        }
        Map<String,Object> result_map = new HashMap<>();
        result_map.put(ResponseResult.Pages,pageInfo.get(ResponseResult.Pages));
        result_map.put(ResponseResult.Page_Size,return_datas.size());
        result_map.put(ResponseResult.Data , return_datas);
        result_map.put(ResponseResult.Status_Code,ServiceStatusCode.Sucess);
        return  result_map;
    }



    private Integer queryUserIsPlusPost(Map<String,Object> param ){
        Integer result = sqlsession.selectOne("com.glsct.api.repository.mapper.PostOptMapper.queryUserIsOperatPost", param);
        if (null == result){
            return 0;
        }
        return result;
    }


    private Map<String,Object> querySimpleUserInfoById(int userId){
        Map<String, Object> simpleUserInfo = sqlsession.selectOne("com.glsct.api.repository.mapper.UserMapper.querySimpleUserInfoById", userId);
        if (null == simpleUserInfo || simpleUserInfo.isEmpty()){
            return new HashMap<>();
        }
        return simpleUserInfo;
    }



    /**
     * 根据postid查询媒体
     * @return
     */
    private List<Map<String,Object>> queryPostMediaByPostId(int post_id){
        List<Map<String,Object>> post_media = sqlsession.selectList("com.glsct.api.repository.mapper.PostsMediaMapper.queryPostMediaByPostId", post_id);
        if (null != post_media){
            return  post_media;
        }
        return new ArrayList<>();
    }

    private  List<Map<String,Object>> queryPostData(Map<String,Object> pageinfo) throws Exception{
        if (null != pageinfo && !pageinfo.isEmpty()){
            List<Map<String,Object>> query_result = sqlsession.selectList("com.glsct.api.repository.mapper.PostsMapper.queryPostsByPages", pageinfo);
            return query_result;
        }
        throw  new GlsctException(ServiceStatusCode.Params_Except);
    }

    private Map<String,Object> computePageInfo(Map<String,Object> requestParms)throws Exception{
        Map<String,Object> oldParam =  requestParms;
        Map<String,Object> newPageInfo =  new HashMap<>();
        int pages = sqlsession.selectOne("com.glsct.api.repository.mapper.PostsMapper.postCountQueryOfType",oldParam);
        int page = Integer.parseInt(oldParam.get(ResponseResult.Page).toString()) ;
        if (page > 0 && page > pages){
            throw  new GlsctException(ServiceStatusCode.No_More_Data);
        }
        int pageSize = Integer.parseInt(oldParam.get(ResponseResult.Page_Size).toString());

        newPageInfo.put(ResponseResult.Page_Size,pageSize);
        newPageInfo.put(ResponseResult.Pages,pages);
        newPageInfo.put(ResponseResult.Page,page);
        return newPageInfo;
    }



}
