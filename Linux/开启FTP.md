1  Linux服务器安装ftp软件

```undefined
sudo apt install vsftpd
```

2  查看ftp安装是否成功及路径

```bash
which vsftpd
```

3  查看ftp 服务器状态, 安装后已经启动

```bash
service vsftpd status
```

4 启动ftp服务器及重启ftp服务器

```bash
service vsftpd start
service vsftpd restart
```

5  查看服务有没有启动

```ruby
#netstat -an | grep 21    //默认端口为21
tcp        0      0 0.0.0.0:21        0.0.0.0:*       LISTEN 
如果看到以上信息，证明ftp服务已经开启。
```

6 如果需要开启root用户的ftp权限要修改以下两个文件

```bash
#vi /etc/vsftpd.ftpusers中注释掉root
#vi /etc/vsftpd.user_list中也注释掉root
然后重新启动ftp服务。
```

7 登录

使用Xftp工具使用账号密码登陆服务器

或者window下，进入cmd命令窗口，输入

```swift
C:\Users\qfyu>ftp
ftp> open 192.168.1.211              //打开服务器IP
连接到 192.168.1.211。
220 (vsFTPd 3.0.3)
200 Always in UTF8 mode.
用户(192.168.1.211:(none)): a        //输入用户名
331 Please specify the password.
密码:                                 //输入密码
230 Login successful.
ftp>
```

