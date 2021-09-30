

### 特点
- 高并发，高性能
- 可扩展：模块化设计
- 高可靠性：不间断运行
- 热部署：不停机的情况下升级Nginx
- BSD许可证

### 应用场景
- 静态资源服务：通过Nginx本地文件系统提供服务，而不是请求应用服务器，能够快速响应请求
- API服务：Nginx直接请求数据库和缓存，不经过应用服务器，可以想象成Nginx自己成了一个应用服务器
- 反向代理服务：包括了Nginx强大的性能，缓存，负载均衡

### Nginx为什么会出现
互联网的数据量快速增长，Apache的性能低下，其一个进程处理一个连接，难以处理几百万，几千万的同时连接

Master-Slave来保证了高可用性，对外只提供一个统一的IP地址，由于Nginx只是转发用户的请求，不保存状态信息，因此实现高可用性是很容易的



### OpenResty
OpenResty是一个集成了Nginx和Lua组件的Web平台，其目标是直接让Web服务直接跑在其服务内部，使用其强大的性能

### 安装
- 下载:https://nginx.org/en/，选择稳定版本，仅复制下载链接
- $ wget 下载链接
- $ tar -zxfv XXX.tar.gz
- `$ cp -r contrib/vim/* ~/.vim/`：使得vim命令具有色彩，更好看
- `./configure --prefix=/home/nginx`
- 如果有如下情况，则执行`sudo apt-get install libpcre3 libpcre3-dev `
```
./configure: error: the HTTP rewrite module requires the PCRE library.
```
- 如果有以下情况，则执行`apt-get install zlib1g apt-get install zlib1g.dev `

```
./configure: error: the HTTP gzip module requires the zlib library.
```

- `make`
- `make install`

配置环境变量
- `vi /etc/profile`
- 添加以下内容
```
export NGINX_HOME=/usr/local/nginx 
export PATH=$PATH:$NGINX_HOME/sbin
```
- `source /etc/profile`
- `nginx -v`：查看效果
- 

### 日志
nginx日志的位置：/usr/local/nginx/logs





### nginx.conf

```nginx
user root;
http {
    server {
        listen 80;
        server_name localhost;
        client_max_body_size 1024M;
        set $doc_root /usr/local/var/www;

        #默认Location
        location / {
            root /usr/local/var/www/html;
            index index.html index.htm;
        }

        location ~/images/ {
            root $doc_root;
        }
        location ~* \-(gifljpgljpegIpng|bmp|ico|swf|css|js)${
            root $doc_root/img;
        }

    }
}
```

server_name是域名

location是地址映射，"/"表示匹配任意路径，当只访问server_name，后面不加任何东西的时候，会访问index指向的页面

set是自定义变量

location优先级从高到底：

(location =) > (location 完整路径) > (location ^~ 路径) > (location ~,~* 正则顺序) > (location 部分起始路径) > (/)



### 反向代理
```nginx
server {
    listen 80;
    server_name localhost;
    location / {
        proxy_pass http://localhost:8081;
        proxy_set_header Host $host:$server_port;
        #设置用户ip地址
        proxy_set_header X-Forwarded-For $remote_addr;
        #当请求服务器出错去寻找其他服务器
        proxy_next_upstream error timeout invalid_header http_500 http_502 http_503;
    }
}   

当浏览器访问localhost:80的时候，会自动访问http://localhost:8081;
```




### 负载均衡
```nginx
upstream web_servers {
    server localhost:8081;
    server localhost:8082;
}
server {
    listen 80;
    server_name localhost;

    location / {
        proxy_pass http://web_servers;
        #必须指定Header Host
        proxy_set_header Host $host: $server _port;
    }
}
服务程序启动两个，端口分别为8081和8082，默认是按照轮询的方式访问服务程序，即1:1
```

```nginx
可以设置权重，机器性能好的可以设置权重更高
backup是当前两个服务宕机后，才会访问，一般不会配置，因为浪费了机器资源
upstream test {
    server localhost:8081 weight=1;
    server localhost:8082 weight=3;
    server localhost:8083 weight=4 backup;
}
```

```nginx
相同的ip发送到相同的服务程序
upstream test {
    ip_hash;
    server localhost:8081 weight=1;
    server localhost:8082 weight=3;
}
```

