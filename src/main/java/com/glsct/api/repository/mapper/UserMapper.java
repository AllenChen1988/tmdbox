package com.glsct.api.repository.mapper;

import com.glsct.api.repository.itf.IMarkMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/23.
 */
public interface UserMapper extends IMarkMapper {

    public Map<String,Object> findUserByUserNameAndPasswd(Map<String,Object> params);

    public Map<String,Object> findUserByThirdPartUUId(Map<String,Object> params);

    @Select("select id,nick_name,photo from tmd_user where id = #{userId}")
    public Map<String,Object> querySimpleUserInfoById(int userId);

    public int registerUser(Map<String,Object> newUserInfo);

    public int registerThirdPartUser(Map<String,Object> tpuserinfo);

    public int modifyUserInfo(Map<String,Object> oldUserInfo);
}
