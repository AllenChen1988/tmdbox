package com.glsct.api.repository.mapper;

import com.glsct.api.repository.itf.IMarkMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
public interface PostsMediaMapper extends IMarkMapper {

    @Select("select media_url,order_no from tmd_post_media where post_id = #{post_id} order by order_no ")
    public List<Map<String,Object>> queryPostMediaByPostId(int post_id);
}
