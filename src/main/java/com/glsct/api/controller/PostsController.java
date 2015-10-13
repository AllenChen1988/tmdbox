package com.glsct.api.controller;

import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServicesType;
import com.glsct.api.constant.url.RequestMapperParam;
import com.glsct.api.constant.url.RequestMapperURL;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/10/7.
 */
@RestController
public class PostsController extends AbstractController {
    /**
     * 帖子发布接口
     * @param param
     * @return
     */
    @RequestMapping(value = RequestMapperURL.USER_POSTS_PUBLISH,method = RequestMethod.POST)
    public @ResponseBody
    Callable<Map<String,Object>> postsPublish(@RequestBody Map<String,Object> param){
        return super.doFeatureRequestServices(ServicesType.User_Posts_Publish,param);
    }

    /**
     * 帖子列表查询
     * @return
     */
    @RequestMapping(value = RequestMapperURL.USER_POSTS_LIST_QUERY,method = RequestMethod.GET)
    public @ResponseBody
    Callable<Map<String,Object>> postsLists(@PathVariable(RequestMapperParam.Posts_Type)String postType ,@PathVariable(RequestMapperParam.Page) String page){
        Map<String,Object> params = new HashMap<>();
        params.put(ResponseResult.Page, page);
        params.put(RequestMapperParam.Posts_Type,postType);
        return super.doFeatureRequestServices(ServicesType.User_Posts_List_Query,params);
    }

}
