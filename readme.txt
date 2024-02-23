环境安装准备

1.mysql
docker run --name mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.36 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

2.redis
docker run --name redis -p 6380:6379 -d redis:7.0

pass 123456

3.RocketMQ
docker run --name namesrv -p 9876:9876 -e "MAX_POSSIBLE_HEAP=100000000" -d rocketmqinc/rocketmq sh mqnamesrv

docker run --name broker  -p 10911:10911 -p 10909:10909 -v
 /usr/local/rocketmq/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf
 --link namesrv:namesrv
 -e "NAMESRV_ADDR=namesrv:9876"
 -e "MAX_POSSIBLE_HEAP=200000000"
 -d rocketmqinc/rocketmq:4.4.0 sh mqbroker
 -c /opt/rocketmq-4.4.0/conf/broker.conf

 docker run --name=rocketmq-console -e "JAVA_OPTS=-Drocketmq.namesrv.addr=192.168.0.200:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 8080:8080 -d styletang/rocketmq-console-ng

4. mongo
docker run --name mongo -p 27017:27017 -d mongo --auth

pass 123456


5.nacos
docker run --name nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:1.1.4

6.Sentinel-Dashboard
docker run --name sentinel  -p 8858:8858 -d  bladex/sentinel-dashboard

Seata-Server
docker run --name seata-server -p 8091:8091  -e SEATA_IP=121.40.140.138 -d seataio/seata-server



