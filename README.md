## WIZARD社区

[我的博客](https://kayleh.github.io/)  


快速运行

安装必备工具
JDK，Maven
克隆代码到本地
运行命令创建数据库脚本
mvn flyway:migrate
运行打包命令
mvn package
运行项目
java -jar target/community-0.0.1-SNAPSHOT.jar
访问项目
http://localhost:8887
资料
java -jar -Dspring.profiles.active=product target/community-0.0.1-SNAPSHOT.jar

Spring 文档
Spring Web
es
Github deploy key
Bootstrap
Github OAuth
Spring
菜鸟教程
Thymeleaf
Spring Dev Tool
Spring MVC
Markdown 插件
UFfile SDK
Count(*) VS Count(1)

工具

Git
Visual Paradigm
Flyway
Lombok
ctotree
Table of content sidebar
One Tab
Live Reload
Postman

脚本

CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN VARCHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
);
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
