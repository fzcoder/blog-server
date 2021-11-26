---
title: Install and Deploy
lang: en-US
tag:
- install
- config
- deploy
---

# 安装部署

## 1、克隆到本地

```bash
$ git clone https://github.com/fzcoder/blog-server.git
```

## 2、添加配置文件

出于安全性问题，本项目并未将`application.properties`文件添加到版本控制，您可以手动在`src/main/resources`文件夹中分别添加`application.properties`、`application-dev.properties`、`application-test.properties`、`application-prod.properties`这四个文件，下面列出这四个文件的内容，您也可以从`Release`版本的附件中获取这四个文件

### application.properties

```properties
#多环境配置
spring.profiles.active=@profileActive@

#阿里云OSS对象存储配置
aliyun.oss.endpoint=<endpoint>
aliyun.oss.accessKeyId=<your id>
aliyun.oss.accessKeySecret=<your secret>
aliyun.oss.bucketName=<bucketName>
aliyun.oss.policy.expire=<expire>
aliyun.oss.maxSize=<maxSize>
aliyun.oss.dir.prefix=<prefix>
aliyun.oss.callback=<callback>
aliyun.oss.bindDomainName=<bindDomainName>

#邮件服务配置
spring.mail.host=smtp.<domain>.com
spring.mail.port=<port>
spring.mail.username=<username>
spring.mail.password=<password>
spring.mail.default-encoding=UTF-8
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.debug=true

#thymeleaf配置
spring.thymeleaf.cache=false

#配置映射文件路径
mybatis-plus.mapper-locations=classpath:xml/*.xml

#JWT相关配置
jwt.iss=<your_iss>
jwt.secret=<your_secret>
jwt.expiration=<expiration_time>
jwt.expiration.remember=<expiration_time>
```

### application-dev.properties

```properties
#端口号
server.port=8081

#跨域配置
http.cors.allowedOrigins=<http|https>://host:<port>

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

#设置c3p0数据源
#连接池中保留的最小连接数
spring.datasource.c3p0.minPoolSize=10
#连接池中保留的最大连接数。Default: 15
spring.datasource.c3p0.maxPoolSize=15
#最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
spring.datasource.c3p0.maxIdleTime=1800
#当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
spring.datasource.c3p0.acquireIncrement=3
spring.datasource.c3p0.maxStatements=0
spring.datasource.c3p0.initialPoolSize=10
#每60秒检查所有连接池中的空闲连接。Default: 0
spring.datasource.c3p0.idleConnectionTestPeriod=60
#定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
spring.datasource.c3p0.acquireRetryAttempts=30
spring.datasource.c3p0.breakAfterAcquireFailure=true
spring.datasource.c3p0.testConnectionOnCheckout=false

#redis数据库配置
spring.redis.database=0
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=<password>
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.max-wait=-1ms
spring.redis.jedis.pool.min-idle=0
```

### application-test.properties

```properties
#端口号
server.port=8081

#跨域配置
http.cors.allowedOrigins=<http|https>://host:<port>

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

#设置c3p0数据源
#连接池中保留的最小连接数
spring.datasource.c3p0.minPoolSize=10
#连接池中保留的最大连接数。Default: 15
spring.datasource.c3p0.maxPoolSize=15
#最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
spring.datasource.c3p0.maxIdleTime=1800
#当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
spring.datasource.c3p0.acquireIncrement=3
spring.datasource.c3p0.maxStatements=0
spring.datasource.c3p0.initialPoolSize=10
#每60秒检查所有连接池中的空闲连接。Default: 0
spring.datasource.c3p0.idleConnectionTestPeriod=60
#定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
spring.datasource.c3p0.acquireRetryAttempts=30
spring.datasource.c3p0.breakAfterAcquireFailure=true
spring.datasource.c3p0.testConnectionOnCheckout=false

#redis数据库配置
spring.redis.database=0
spring.redis.host=<host>
spring.redis.port=6379
spring.redis.password=<password>
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.max-wait=-1ms
spring.redis.jedis.pool.min-idle=0
```

### application-prod.properties

```properties
#端口号
server.port=443

#跨域配置
http.cors.allowedOrigins=<http|https>://host:<port>

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

#设置c3p0数据源
#连接池中保留的最小连接数
spring.datasource.c3p0.minPoolSize=10
#连接池中保留的最大连接数。Default: 15
spring.datasource.c3p0.maxPoolSize=15
#最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
spring.datasource.c3p0.maxIdleTime=1800
#当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
spring.datasource.c3p0.acquireIncrement=3
spring.datasource.c3p0.maxStatements=0
spring.datasource.c3p0.initialPoolSize=10
#每60秒检查所有连接池中的空闲连接。Default: 0
spring.datasource.c3p0.idleConnectionTestPeriod=60
#定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
spring.datasource.c3p0.acquireRetryAttempts=30
spring.datasource.c3p0.breakAfterAcquireFailure=true
spring.datasource.c3p0.testConnectionOnCheckout=false

#redis数据库配置
spring.redis.database=0
spring.redis.host=<host>
spring.redis.port=6379
spring.redis.password=<password>
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.max-wait=-1ms
spring.redis.jedis.pool.min-idle=0

#SSL配置
#证书位置
server.ssl.key-store=classpath:ssl/<filename>.jks
#密钥库密码
server.ssl.key-password=<password>
#证书类型
server.ssl.key-store-type=JKS
#证书别名
server.ssl.key-alias=<alias>
# 设置监听端口
tomcat.listen.port=80
# 设置重定向端口
tomcat.redirect.port=443
```

注：以上配置文件请根据实际情况进行修改

## 3、添加邮件模板

邮件的模板需要放在`src/main/resources/templates`目录下，模板内容如下：

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>邮箱验证</title>
    <meta charset="utf-8">
</head>

<body>
    <!-- 头部 -->
    <div style="padding: 10px; background-color: #393D49;">
        <h2 style="color: #FFFFFF; margin: 0px;">Title</h2>
    </div>
    <!-- 内容 -->
    <div style="padding-top: 10px; padding-bottom: 10px;">
        <div style="background-color: snow; padding: 20px;">
            <div>
                <h3>尊敬的用户：您好！</h3>
                <p>说明：您现在正在进行敏感操作，为了确保您的账户安全，我们将通过邮件对您进行身份验证。</p>
                <p th:text="${message}"></p>
                <div>
                    <h4>本次的验证码为：</h4>
                    <div style="background-color: #EBEEF5; padding: 10px;">
                        <h3 th:text="${code}"></h3>
                    </div>
                    <h4>有效期为5分钟</h4>
                </div>
                <p style="margin-top: 15px;">发件人：XXX</p>
            </div>
        </div>
    </div>
    <!-- 页底 -->
    <div style="padding: 10px; text-align: center; background-color: #2F4056;">
        <p style="margin: 0px; color: #FFFFFF;">Copyright © 2020 <a href="https://domain.com/" style="color: #FFFFFF;">https://domain.com</a> All Rights Reserved.</p>
    </div>

</body>

</html>
```

## 4、运行项目

在项目根目录下打开终端，输入以下命令：

```shell
$ mvn clean package -P <environment name>
```

其中`environment name`根据实际情况进行填写，下面给出填写规则：

| 环境     | environment name |
| -------- | ---------------- |
| 开发环境 | dev              |
| 测试环境 | test             |
| 生产环境 | prod             |

当项目打包成功之后进入`target/`目录执行以下命令启动项目：

```shell
$ java -jar <package name>.jar
```
