## Performance test

__Jmeter__

### capacity issue

1. check configuration of SpringBoot

​	view each configuration under __spring-configuration-metadata.json__:

​		__server.tomcat.accept-count:__ length of wating queue, default 100

​		__server.tomcat.max-connections:__ maximum of connections,default 10000

​		__server.tomcat.max-threads:__maximum working thread,dafault 200

​		__server.tomcat.min-spare-threads:__ minimum working thread, default 10

​	

​	customise Tomcat embedded configuration:

​		use __WebServerFactoryCustomizer<ConfigurableServeletWebServerFactory>__

​		__KeepAliveTimeOut:__ cut keepalive after *** seconds

​		__maxKeepAliveRequests:__ keepalive become voidness after *** times request

​	under default configuration, connection denied when connection beyond 10000

​	under default configuration, request denied when request beyond 200 + 100



2. Limit of single web container:

​		number of thread: when thread beyound 800-1000, 4 core CPU, 8G memory will cost bunch of time on CPU scheduling

​		waiting queue as buffer pool, but it can't goes unlimited long, because it cost memory and cost cpu when push and pop;



3. QPS (millions of data)

   PK query: 1-10 millisecond

   unique index: 10 - 100 millisecond

   nonunique index: 100 - 1000 millisecond

   no index: 1000+

4. TPS



## Nginx reverse proxy and load balance

Start Nginx: sbin/nginx -c conf/nginx.conf 		reload: sbin/nginx -s reload

Horizontal scaling

![image text](<https://github.com/SecretsCC/eBusinessSeckilling/imgs/nginx%20scale%20out.png>)

Mysql open remote access service

Dynamica-static seperation

reverse proxy

![image text](<https://github.com/SecretsCC/eBusinessSeckilling/imgs/nginx%20strategy.png>)

​	





## Linux command

check the number of thread: __pstree__ -p <pid> | wc -l

show system information: __top -H__