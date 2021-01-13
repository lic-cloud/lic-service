# lic-service
## 系统运行前设置
1.使用的JDK版本为11
2.创建名为lic的本地数据库 无需运行sql文件 
  系统运行自动初始化数据：lic-starter/src/main/resources/import.sql
3.修改config文件夹下application.yml里面的配置
datasource:
    url: xxx
    username:xxx
    password:xxx
jpa:
    hibernate:
    ddl-auto: create或是none
    #create：重新运行程序会初始化数据库
    #none：保留数据库的数据不做操作
4.运行本地redis
5.运行系统 lic-starter/src/main/java/cn/bestsort/Main.java
