package com.platform.data.jdbc.plugin;

/**
 * @author Advance
 * @date 2022年01月18日 14:26
 * @since V1.0.0
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.platform.core.base.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * SQL日志记录
 */
@Component
@Intercepts({
@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class }),
@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SqlLogInterceptor implements MybatisInterceptor{
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(SqlLogInterceptor.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject instanceof BaseEntity
                && StringUtils.containsIgnoreCase(boundSql.getSql(), "update ")
                && StringUtils.containsIgnoreCase(boundSql.getSql(), " set ")) {
            BeanUtil.setProperty(parameterObject, "updateTime", new Date());
        }
        if(logger.isDebugEnabled()) {
            Configuration configuration = mappedStatement.getConfiguration();
            String method = invocation.getMethod().getName();
            String sql = showSql(configuration, boundSql);
            String opttype = "查询数据记录";

            if (sql != null && StringUtils.equals(method, "update")) {
                String tmpSql = sql.toLowerCase();
                if (StringUtils.containsIgnoreCase(tmpSql, "update ") && StringUtils.containsIgnoreCase(tmpSql, " set ")) {
                    opttype = "更新数据记录";

                } else if (StringUtils.containsIgnoreCase(tmpSql, "insert ") && StringUtils.containsIgnoreCase(tmpSql, " into ") && StringUtils.containsIgnoreCase(tmpSql, "values")) {
                    opttype = "插入数据记录";

                } else if (StringUtils.containsIgnoreCase(tmpSql, "delete ") && StringUtils.containsIgnoreCase(tmpSql, " from ")) {
                    opttype = "删除数据记录";

                }
            }
            long start = System.currentTimeMillis();
            try {
                return invocation.proceed();
            }
            catch (Throwable e) {
                throw e;
            }
            finally {
                logger.debug("操作类型：[" + opttype + "]，SQL：[" + sql + "] 执行时间：[" + (System.currentTimeMillis() - start) + "毫秒]");
            }
        }
        else {
            return invocation.proceed();
        }
    }

    /**
     * 获取输入类及输入类的父类的所有field
     * @param tCls Class
     * @return Field[]
     */
    private Field[] getAllFieldBySelfAndSuperClass(Class tCls) {
        List<Field> fieldList = new ArrayList<>();
        while (tCls != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(tCls.getDeclaredFields())));
            tCls = tCls.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * @param configuration Configuration
     * @param boundSql BoundSql
     * @return String
     */
    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            }
            else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                    else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * @param obj Object
     * @return String
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        }
        else if (obj instanceof Date) {
            value = "'" + DateUtil.format((Date)obj,"yyyy-MM-dd") + "'";
        }
        else if (obj != null) {
            value = obj.toString();
        }
        else {
            value = "";
        }
        // 处理特殊字符$, 会引发正则group引用
        if (value.contains("$")) {
            StringBuilder sb = new StringBuilder();
            char[] chs = value.toCharArray();
            for (char ch : chs) {
                if (ch == '$') {
                    sb.append("\\");
                }
                sb.append(ch);
            }
            value = sb.toString();
        }

        return value;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
