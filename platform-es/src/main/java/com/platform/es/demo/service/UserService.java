package com.platform.es.demo.service;

import com.platform.es.demo.entity.User;
import com.platform.es.demo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 10:37
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final SqlSessionFactory sqlSessionFactory;
    @Autowired
    private UserMapper userMapper;

    public void batchInsert(List<User> users) {
        log.info("开始批量插入数据，总条数: {}", users.size());
        long startTime = System.currentTimeMillis();

        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            for (int i = 0; i < users.size(); i++) {
                mapper.insert(users.get(i));
                // 每 1000 条打印一次进度
                if (i % 1000 == 0 && i > 0) {
                    log.info("已处理 {} 条数据", i);
                }
            }
            session.commit();
            log.info("批量插入完成，耗时 {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("批量插入失败: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    public void batchInsertUsers(List<User> users) {
        userMapper.insertBatchSomeColumn(users); // 使用MyBatis-Plus的批量插入方法
    }
}
