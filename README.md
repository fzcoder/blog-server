##  BLOG-SERVER

一个采用 [Spring Boot](https://spring.io/projects/spring-boot) 和 [Mybatis](https://mybatis.org/mybatis-3/) 框架编写的个人博客网站服务端应用程序，可作为静态博客使用者的在线博客编辑和下载网站。

### 一、项目简介

本项目使用 [Spring Boot](https://spring.io/projects/spring-boot) 框架，并选用 [Mybatis](https://mybatis.org/mybatis-3/) 作为持久层框架，[Spring Security](https://spring.io/projects/spring-security) 作为安全框架，数据库选用 [MySQL](https://www.mysql.com/) ，缓存数据库选用 [Redis](https://redis.io/)。除此之外，还集成了 [MyBatis-Plus](https://github.com/baomidou/mybatis-plus) 和 [lombok](https://projectlombok.org/)，方便快速开发应用。

项目的后台管理端地址为: [https://github.com/fzcoder/blog-admin](https://github.com/fzcoder/blog-admin)

项目的 Web 端地址为: [https://github.com/fzcoder/blog-web](https://github.com/fzcoder/blog-web)

注：自`v2.0`版本起，本项目将不再支持该项目的 Web 端，若需支持 Web 端，请下载`v1.x`版本。（本人更加推荐使用静态博客作为 Web 端）

### 二、功能特性

本项目自`v2.0`版本起将支持在下载文章时添加一些静态博客（如：[hexo](https://github.com/hexojs/hexo)、[hugo](https://github.com/gohugoio/hugo) 等）所需要的一些头部信息，方便配合静态博客。关于本项目更多的功能特性，请详见 [Release](https://github.com/fzcoder/blog-server/releases) 版本说明。

### 三、安装部署

#### 1、克隆到本地

```bash
$ git clone https://github.com/fzcoder/blog-server.git
```

#### 2、添加配置文件

出于安全性问题，本项目并未将`application.properties`文件添加到版本控制，您可以手动在`src/main/resources`文件夹中分别添加`application.properties`、`application-dev.properties`、`application-test.properties`、`application-prod.properties`这四个文件，下面列出这四个文件的内容，您也可以从`Release`版本的附件中获取这四个文件

##### application.properties

```properties
#多环境配置
spring.profiles.active=@profileActive@

#设置c3p0数据源
spring.datasource.type=com.mchange.v2.c3p0.ComboPooledDataSource

#跨域配置
http.cors.allowedOrigins=<http|https>://host:<port>

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

#SSL配置
#若无需配置SSL可将下面相关配置注释掉或删除,此外还需将TomcatConfig.java类注释掉或删除
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

##### application-dev.properties

```properties
#端口号
server.port=8081

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

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

##### application-test.properties

```properties
#端口号
server.port=8081

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

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

##### application-prod.properties

```properties
#端口号
server.port=443

#mysql数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:3306/<database_name>?useAffectedRows=true
spring.datasource.username=<username>
spring.datasource.password=<password>

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

注：以上配置文件请根据实际情况进行修改

#### 3、运行项目

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

