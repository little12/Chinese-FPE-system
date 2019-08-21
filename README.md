#资料
[Spring 文档](https://spring.io/guides/)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es](https://elasticsearch.cn/explore)  
[Bootstrap3](https://v3.bootcss.com/getting-started/)   
[Github OAuth](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)   
[spring boot官方文档](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-sql.html#boot-features-configure-datasource)   
[spring Web MVC](https://docs.spring.io/spring/docs/5.2.0.M3/spring-framework-reference/web.html#spring-web)
#工具
[Git](https://git-scm.com/download)
[visual Paradim](https://www.visual-paradigm.com)   
[POST请求-okhttp](https://square.github.io/okhttp/)   
[fastjson](https://mvnrepository.com/search?q=fastjason)    
[H2 JDBC](http://www.h2database.com/html/main.html)     
[maven repository](https://mvnrepository.com/)可以在里面搜索很多依赖   
[spring boot mybatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)    
[flyway（管理数据库）](https://flywaydb.org/getstarted/firststeps/maven)   
[Lombok](https://www.projectlombok.org/)    用于将get.set方法利用注解简化  
[自动部署 developer tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)
[html模板](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#difference-between-thinsert-and-threplace-and-thinclude)
#脚本
```sql
create table USER
(
    ID           INTEGER auto_increment,
    NAME         VARCHAR(50),
    ACCOUNT_ID   VARCHAR(100),
    TOKEN        VARCHAR(50),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT
);
```
```bash
rm ./communitytest
mvn flyway:migrate
```