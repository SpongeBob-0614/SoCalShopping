# SoCalShopping - Getting Started
Create a distributed system web project based on SpringBoot, Nosql, rocketMQ, etc.

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#web)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## Redis
Install redis：
sudo apt install redis

Start Redis：
open wls and type: redis-server &

Verify redis：
redis-cli ping

Redis server port: 6379

## Jmeter
Open folder: E:\apache-jmeter-5.6.3\bin and click jmeter.bat (or type jmeter.bat in win search)


## RocketMQ

Enter CMD go to Folder E:\rocketmq-4.9.3\bin
Type
`start mqnamesrv`
Launch a new terminal with:
`start mqbroker -n localhost:9876 autoCreateTopic=true`
Delete Message:
`./mqadmin deleteTopic -c DefaultCluster -n localhost:9876 -t {topicName}`

### Check Port:

netstat -ano | findstr "9876"

### ShutDown RocketMQ

mqshutdown broker
mqshutdown namesrv
如果不是第一次运行rocketmq，则将C:\Users\Administrator\store文件夹下的文件全部删除，则可以正常启动

### Delete Message:

`./mqadmin deleteTopic -c DefaultCluster -n localhost:9876 -t {topicName}`

### Environment setup

Key: ROCKETMQ_HOME
Val:  E:\rocketmq-4.9.3\bin

### Elasticsearch

Enter CMD go to Folder D:\elasticsearch-7.4.2\bin
type
`elasticsearch`

Enter CMD goto folder D:\kibana-7.4.2-windows-x86_64\kibana-7.4.2-windows-x86_64\bin
type
`kibana.bat`
visit http://127.0.0.1:5601/


### Install Jar

https://maven.apache.org/download.cgi
Update POM with pacakage Jar and plugins for maven
In Plugins do compile and then jar
Copy from target to root path, then type:

```
mvn install:install-file -Dfile=target/OnlineShopping_02-1.0.jar -DgroupId=com.qiuzhitech -DartifactId=OnlineShopping -Dversion=1.1 -Dpackaging=jar
```