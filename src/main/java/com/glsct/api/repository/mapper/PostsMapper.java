package com.glsct.api.repository.mapper;

import com.glsct.api.repository.itf.IMarkMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/23.
 */
public interface PostsMapper extends IMarkMapper {

    @Select("select id,type,content,user_id,create_time from tmd_post where type = #{postType} order by create_time desc  limit #{page} , #{pageSize}")
    public List<Map<String,Object>> queryPostsByPages(Map<String, Object> params);

    @Select("select   ceiling(count(1) / #{pageSize})   cnt from tmd_post where type=#{postType}")
    public int postCountQueryOfType(Map<String,Object> params);

    public int postPublish(Map<String,Object> params);

    public int postMediaPublish(Map<String,Object> params);

}
