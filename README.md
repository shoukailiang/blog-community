# blog-community
## 项目前后端不分离
## 使用github授权登录
[github授权登录--Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
## 授权文档
[文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

## 数据库
[h2:一个内嵌的数据库](https://www.h2database.com/html/main.html)
## 数据库迁移
[flyway](https://flywaydb.org/getstarted/) 
```sh
在pom中配置后可运行mvn flyway:migrate
```
## MyBatis Generator With Maven
```sh
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

## markdown 编辑器
[Editor.md](https://pandao.github.io/editor.md/)

## markdown编辑器的图片上传
[ucloud的对象存储](https://docs.ucloud.cn/storage_cdn/ufile/index)
## sdk
[UFile(OSS) SDK for Java from UCloud](https://github.com/ucloud/ufile-sdk-java)

## 快速运行
```sh
cp 一个 application-production.properties 并填写相应配置
mvn flyway:migrate
mvn clean compile package 
java -jar -Dspring.profiles.active=production /target/xxxxx.jar
访问 http://localhost:8888
```
