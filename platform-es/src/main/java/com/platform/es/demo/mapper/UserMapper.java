package com.platform.es.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.platform.es.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 18:35
 */
@Repository
//public interface UserMapper extends BaseMapper<User> {
public interface UserMapper extends MyBaseMapper<User> {
    void batchInsert(User user);
    void insertSelective(User user);

    int insert(User entity);
}
