package com.platform.quartza.service.notify;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.platform.quartza.entity.BasePostgreResponse;
import com.platform.quartza.mapper.PostgreSqlNotifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * postgresql 数据库通知服务
 * @author: Advance
 * @create: 2021-12-04 10:28
 * @since V1.0.0
 */
@Component
@DS("slave_1")
public class PostgreSqlNotifyService {
    @Resource
    private PostgreSqlNotifyMapper postgreSqlNotifyMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public BasePostgreResponse pgNotifyTest(){
        return postgreSqlNotifyMapper.pgNotifyTest();
    }

    @DS("slave_1")
    public BasePostgreResponse jdbcPgNofifyTest(){
        BasePostgreResponse resp = new BasePostgreResponse();
        List<BasePostgreResponse> query = jdbcTemplate.query("select * from f_test_pg_notify('1')", new RowMapper<BasePostgreResponse>() {
            @Override
            public BasePostgreResponse mapRow(ResultSet resultSet, int i) throws SQLException {
                resp.setRetcode(resultSet.getInt("retcode"));
                resp.setRetvalue(resultSet.getString("retvalue"));
                return resp;
            }
        });

        return !CollectionUtils.isEmpty(query)?query.get(0):new BasePostgreResponse();
    }

}
