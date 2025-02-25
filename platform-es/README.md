# 基于ELK（es、logstash、kibana）和springboot2、log4j2实现日志可视化 工程搭建

以下是基于 ELK（Elasticsearch、Logstash、Kibana）与 Spring Boot 2.x、Log4j2 实现日志可视化的完整工程搭建指南。流程分为 **日志采集**（Spring Boot + Log4j2）、**日志传输**（Logstash）、**日志存储与可视化**（Elasticsearch + Kibana）三个核心步骤。

---

### **1. 环境准备**
+ **ELK 组件**：
    - Elasticsearch 7.x/8.x（数据存储与检索）
    - Logstash 7.x/8.x（日志收集与处理）
    - Kibana 7.x/8.x（数据可视化）
    - 下载地址：[ELK 官方下载页](https://www.elastic.co/downloads)
+ **Spring Boot 2.x**：使用 Log4j2 作为日志框架。
+ **Java 8+**：确保已安装 JDK。

---

### **2. Spring Boot 项目配置**
#### **(1) 添加 Log4j2 依赖**
在 `pom.xml` 中 **排除默认 Logback**，并引入 Log4j2：

```xml
<!-- 排除 Spring Boot 默认的 Logback -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 引入 Log4j2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>

```

#### **(2) 配置 Log4j2**
创建 `log4j2.xml`（或 `log4j2-spring.xml`），配置日志输出到文件并发送到 Logstash：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO">
    <Appenders>
        <!-- 输出到控制台 -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        <!-- 输出到文件（按日期滚动） -->
        <RollingFile name="File" fileName="logs/app.log"
                     filePattern="logs/app-%d{yyyy-MM-dd}.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
            </Policies>
        </RollingFile>
        <!-- 发送日志到 Logstash（Socket 方式） -->
        <Socket name="Logstash" host="localhost" port="5044" protocol="TCP">
            <JsonLayout compact="true" eventEol="true"/>
        </Socket>
    </Appenders>
    <Loggers>
        <Root level="INFO">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
            <AppenderRef ref="Logstash"/>
        </Root>
    </Loggers>
</Configuration>

```

---

### **3. Logstash 配置**
#### **(1) 创建 Logstash 配置文件**
新建 `logstash-springboot.conf`，定义输入、过滤、输出规则：

```nginx
input {
  tcp {
    port => 5044         # 与 Log4j2 中的端口一致
    codec => json_lines  # 解析 JSON 格式日志
  }
}

filter {
  # 可选：使用 Grok 解析日志内容（根据实际日志格式调整）
  grok {
    match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} \[%{DATA:thread}\] %{LOGLEVEL:loglevel} %{DATA:logger} - %{GREEDYDATA:message}" }
  }
  date {
    match => [ "timestamp", "yyyy-MM-dd HH:mm:ss" ]
    target => "@timestamp"  # 覆盖默认时间字段
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]  # Elasticsearch 地址
    index => "springboot-logs-%{+YYYY.MM.dd}"  # 按天创建索引
  }
  stdout { codec => rubydebug }  # 可选：输出到控制台调试
}
```

#### **(2) 启动 Logstash**
```bash
./bin/logstash -f logstash-springboot.conf
```

---

### **4. Elasticsearch & Kibana 配置**
#### **(1) 启动 Elasticsearch**
```bash
./bin/elasticsearch  # 默认端口 9200
```

#### **(2) 启动 Kibana**
```bash
./bin/kibana  # 默认端口 5601
```

---

### **5. Kibana 可视化**
1. **创建索引模式**  
    - 访问 `http://localhost:5601`，进入 **Management > Stack Management > Index Patterns**。
    - 输入索引名称 `springboot-logs-*`，选择时间字段 `@timestamp`。
2. **查询与分析日志**  
    - 进入 **Discover** 页面，选择索引模式，查看实时日志。
    - 使用 KQL（Kibana Query Language）过滤日志。
3. **创建仪表盘**  
    - 进入 **Visualize Library**，基于日志字段（如 `loglevel`、`message`）创建图表（柱状图、饼图等）。
    - 将图表组合到 **Dashboard** 中。

---

### **6. 测试流程**
1. 启动 Spring Boot 应用，生成日志。
2. 检查 Logstash 控制台是否有日志输入。
3. 在 Kibana Discover 中确认日志是否写入 Elasticsearch。

---

### **7. 高级配置**
#### **(1) 日志字段解析优化**
+ 在 Logstash 的 `filter` 中使用 `grok` 或 `dissect` 解析复杂日志格式。
+ 示例解析 Spring Boot 默认日志格式：

```nginx
grok {
  match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} %{LOGLEVEL:loglevel} %{NUMBER:pid} --- \[%{DATA:thread}\] %{DATA:class} : %{GREEDYDATA:message}" }
}
```

#### **(2) 安全加固**
+ Elasticsearch 和 Kibana 启用安全认证（X-Pack 基础版免费）。
+ 使用 Nginx 反向代理 Kibana，配置 HTTPS。

#### **(3) 性能优化**
+ 调整 Logstash 的 `pipeline.workers`（CPU 核数）。
+ 配置 Elasticsearch 的 JVM 堆内存（`-Xms4g -Xmx4g`）。

---

### **注意事项**
1. **版本一致性**：确保 ELK 各组件版本一致（如 7.17.x 或 8.x）。
2. **防火墙配置**：开放 Elasticsearch（9200）、Logstash（5044）、Kibana（5601）端口。
3. **日志轮转**：在 Log4j2 中配置合理的日志文件滚动策略，避免磁盘占满。
4. **资源监控**：使用 Elasticsearch 的 Monitoring 功能或 Prometheus + Grafana 监控集群状态。

通过以上步骤，可实现 Spring Boot 应用日志的全链路采集、存储与可视化。

