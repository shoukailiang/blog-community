# community
## 使用github授权登录
[github授权登录--Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
## 授权文档
[文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

## 数据库
mysql
## 数据库迁移
[flyway](https://flywaydb.org/getstarted/) 
```bash
在pom中配置后可运行mvn flyway:migrate
```
## MyBatis Generator With Maven
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

## markdown 编辑器
[Editor.md](https://pandao.github.io/editor.md/)

## markdown编辑器的图片上传
[ucloud的对象存储](https://docs.ucloud.cn/storage_cdn/ufile/index)
## sdk
[UFile(OSS) SDK for Java from UCloud](https://github.com/ucloud/ufile-sdk-java)

## 运行
```shell
cp 一个 application-production.properties
mvn clean compile package 
java -jar -Dspring.profiles.active=production /target/xxxxx.jar
```