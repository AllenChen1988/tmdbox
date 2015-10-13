package com.glsct.api.repository.mapper;

import com.glsct.api.repository.itf.IMarkMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
public interface PostOptMapper extends IMarkMapper {

    @Select("select is_good  from tmd_post_opt where post_id = #{post_id} and user_id = #{user_id}")
    public Integer queryUserIsOperatPost(Map<String,Object> param);
}
