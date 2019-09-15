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



Distrebuted Session: save session info into redis

​	cookie transfer sessionid: java tomcat container session transfer to redis

​	token transfer sessionid: redis 

用redis解决分布式会话管理的问题



## Redis

cache

data need to be serializable before save it in to redis

Model:

 	1. single model
 	2. sentinal model
 	3. cluster model





## Multi-Level Cache	 多级缓存

### Cache Design

​	(1) use quick access devices, like memory

​	(2) save cache near the user 

​	(3) clean dirty cache

### Multi-Level Cache

​	(1) redis Cache

​	(2) memory hotspot local cache

​	(3) nginx proxy cache

​	(4) nginx lua cache	



协程

nginx lua



## CDN 

cache control头:

​	private 		     客户端可以缓存

​	public			客户端和代理服务器都可以缓存

​	max-age = xxx	 缓存的内容在

​	no-cache		强制向服务器再验证一次

​	no-store 		不缓存请求的任何返回内容



浏览器三种刷新方式

​	回车刷新或a链接: 看cache-control对应的max-age是否仍然有效,有效则直接from cache, 若cache-control中为no-cache,则进入缓存协商逻辑

​	F5刷新或command+Rs刷新: 去掉cache-control中的max-age或直接设置max-age为0,然后进入缓存协商逻辑

​	ctrl+F5或commond+shift+R刷新: 去掉cache-control和协商头,强制刷新



静态资源部署策略:

 	1. 加版本号: 不便利且维护困难
 	2. 带摘要部署,存在先部署html还是先部署资源的覆盖问题
 	3. 使用摘要做文件名部署





全页面静态化 phantomjs

​	initView和hasInit阻止初始化

​	