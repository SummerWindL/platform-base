# 在Spring Boot中整合Sharding-JDBC、MySQL和MyBatis-Plus实现批量插入并分库分表

---
TODO：
项目调整引入 platform-data 实现多数据源控制，但日志打印需要优化
>> Sharding-JDBC是当当网研发的开源分布式数据库中间件，
>
> 从 3.0 开始Sharding-JDBC被包含在 Sharding-Sphere 中，
>
> 之后该项目进入进入Apache孵化器，4.0版本之后的版本为Apache版本。 ShardingSphere是一套开源的分布式数据库中间件解决方案组成的生态圈，
>
> 它由Sharding-JDBC、ShardingProxy和Sharding-Sidecar（计划中）这3款相
>
> 互独立的产品组成。 他们均提供标准化的数据分片、分布式事务和
>
> 数据库治理功能，可适用于如Java同构、异构语言、容器、云原生等各种多样化的应用场景。
>
> 
>
> Sharding-JDBC，它定位为轻量级Java框架，在Java的JDBC层提供的额外服务。 
>
> 它使用客户端 直连数据库，以jar包形式提供服务，无需额外部署和依赖，
>
> 可理解为增强版的JDBC驱动，完全兼容JDBC和各种 ORM框架。
>
> 
>
> Sharding-JDBC的核心功能为数据分片和读写分离，通过Sharding-JDBC，
>
> 应用可以透明的使用jdbc访问已经分库
>
> 分表、读写分离的多个数据源，而不用关心数据源的数量以及数据如何分布。
>



在Spring Boot中整合Sharding-JDBC、MySQL和MyBatis-Plus实现批量插入并分库分表的步骤如下：

---

### 1. 添加依赖（pom.xml）
```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- MyBatis-Plus -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.1</version>
    </dependency>
    
    <!-- Sharding-JDBC -->
    <dependency>
        <groupId>org.apache.shardingsphere</groupId>
        <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
        <version>5.1.1</version>
    </dependency>
    
    <!-- MySQL驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>

```

---

### 2. 配置分库分表规则（application.yml）
```yaml
spring:
  shardingsphere:
    # 数据源配置（定义两个库ds0和ds1）
    datasource:
      names: ds0, ds1
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/ds0?useSSL=false&serverTimezone=UTC
        username: root
        password: root
      ds1:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/ds1?useSSL=false&serverTimezone=UTC
        username: root
        password: root
    
    # 分片规则
    rules:
      sharding:
        # 分库策略（根据user_id取模分2个库）
        default-database-strategy:
          standard:
            sharding-column: user_id
            sharding-algorithm-name: db-inline
        # 分表策略（根据user_id取模分2张表）
        tables:
          user:
            actual-data-nodes: ds$->{0..1}.user_$->{0..1}
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: table-inline
            key-generate-strategy:
              column: id
              key-generator-name: snowflake
        
        # 分片算法
        sharding-algorithms:
          db-inline:
            type: INLINE
            props:
              algorithm-expression: ds$->{user_id % 2}
          table-inline:
            type: INLINE
            props:
              algorithm-expression: user_$->{user_id % 2}
        
        # 分布式序列算法（雪花算法）
        key-generators:
          snowflake:
            type: SNOWFLAKE
```

---

### 3. 实体类与Mapper接口
```java
// 实体类（对应逻辑表user）
@Data
@TableName("user") // MyBatis-Plus注解，逻辑表名
public class User {
    @TableId(type = IdType.ASSIGN_ID) // Sharding-JDBC分布式ID
    private Long id;
    private Long userId;
    private String name;
    private Integer age;
}

// Mapper接口
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

---

### 4. 批量插入数据
```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    public void batchInsertUsers(List<User> users) {
        userMapper.insertBatchSomeColumn(users); // 使用MyBatis-Plus的批量插入方法
    }
}
```

> **注意**：确保`application.yml`中开启批量操作：
>

```yaml
mybatis-plus:
  global-config:
    db-config:
      id-type: assign_id
  configuration:
    jdbc-type-for-null: null
```

---

### 5. 测试分库分表
```java
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Test
    public void testBatchInsert() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserId((long) i); // user_id为分片键
            user.setName("User" + i);
            user.setAge(20 + i);
            users.add(user);
        }
        userService.batchInsertUsers(users);
    }
}
```

---

### 6. 验证结果
+ **分库**：`user_id`为奇数的数据插入`ds1`，偶数的插入`ds0`。
+ **分表**：`user_id`为奇数的数据插入`user_1`，偶数的插入`user_0`。

检查两个库中的表数据分布是否符合预期：

```sql
-- 检查ds0.user_0和ds0.user_1
SELECT * FROM ds0.user_0;
SELECT * FROM ds0.user_1;

-- 检查ds1.user_0和ds1.user_1
SELECT * FROM ds1.user_0;
SELECT * FROM ds1.user_1;
```

---

### 关键配置说明
1. **分片算法**：通过`user_id`取模决定数据路由到哪个库和表。
2. **分布式ID**：使用Snowflake算法生成唯一主键，避免主键冲突。
3. **批量插入优化**：MyBatis-Plus的`insertBatchSomeColumn`方法会生成一条`INSERT INTO ... VALUES (...), (...)`语句，提升批量插入性能。

---

### 常见问题
+ **主键冲突**：确保使用分布式ID生成策略（如Snowflake）。
+ **SQL路由错误**：检查分片键（如`user_id`）是否在插入时正确赋值。
+ **批量插入性能**：调整MySQL的`rewriteBatchedStatements=true`参数以优化批量插入。

详细参考：https://blog.csdn.net/Alian_1223/article/details/131172257