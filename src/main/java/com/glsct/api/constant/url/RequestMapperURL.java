package com.glsct.api.constant.url;

/**
 * Created by Administrator on 2015/10/6.
 */
public interface RequestMapperURL {

    /**
     * 文件上传
     */
    public final static String FILE_UPLOAD = "/api/v1/upload" ;

    /**
     * 用户登录
     */
    public final static String USER_LOGIN = "/api/v1/login";
    /**
     * 用户发帖
     */
    public final static String USER_POSTS_PUBLISH = "/api/v1/posts";
    /**
     * 帖子分页查询
     */
    public final static String USER_POSTS_LIST_QUERY = "/api/v1/posts/{"+RequestMapperParam.Posts_Type+"}/{"+RequestMapperParam.Page + "}";

}
