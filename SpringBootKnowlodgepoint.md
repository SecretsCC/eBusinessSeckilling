# SpringBoot

## annotation 

1. __@EnableAutoConfiguration__

   ​	annotation auto-configures the beans that are present in the classpath. This simplifies the developers work by guessing the required beans from the classpath and configure it to run the application. 

2. __@RestController__

3. @RequestMapping()







# Nginx

 distributed system

### nginx web server

​	sbin/nginx -s reload: restart after change configuration

Dynamic-static separation

​	location/resources/:   specify folder for static resources

​	location for dynamic resources

### reverse proxy

 	1. configure upstream server
 	2. configure path for proxy pass
 	3. start tomcat access log verification



configure keep alive in nginx:

​	proxy_http_version 1.1

​	proxy_set_header Connection ""; 置空



The reason why nginx is high efficiency:

​	epoll multiplexing 多路复用, 解决了IO阻塞回调通知的问题

​		变更出发回调后直接读取,理论上没有上线

​	master worker  可以平滑重启

​	协程机制, 协程比线程还小, 不花费CPU, 只花费内存

###  Session 

​	- cookie transfer sessionid: java tomcat session

​	- token transfer sessionid: java code session



Distrebuted Session:

​	cookie transfer sessionid: java tomcat container session transfer to redis

​	token transfer sessionid: redis 

​	

​	

​	