- `scp source target`：从source发送文件到target
    - `scp root@192.168.53.211:/home/abc.log /wasHome/`
- 显示命令的使用说明：`man COMMAND`
- 下载文件：`wget http:XXX`
- 改名/ 移动文件：`mv sourceFILE/DIR destFILE/destDIR`
- 复制文件：`cp [option] source dest`
    - -rf：对于文件夹，迭代复制，而且不提示。
    - 复制文件内的所有内容：`cp -rf ./a/* ./target`
- 设置备份：`cp --force --backup=numbered a.txt a.txt`
- 压缩文件：`zip -r a.zip ./web`
- 解压文件：`tar -zxvf XXX.tar.gz`
- 解压zip文件：`unzip XXX.zip`
- 创建目录：mkdir -p server/log{0..2}
    - -p：表示目录不存在则自动创建，比如server不存在则会自动创建server目录
    - 该语句最终会在server目录下创建3个子目录，分别为log0，log1，log2
- `rm -rf /`：强制删除根目录下的东西
- `rm -rf *`：强制删除当前目录的所有文件
- `rm -rf .`：强制删除当前文件及及其子文件夹
- `chmod a+x file`：对于所有的用户组，添加执行权限，+r是阅读权限，+w是写入权限
- `tail -f filename`：监视文件
- `cat filename`：从第一行开始显示
- `tac filename`：从最后一行开始显示
- `more filename`：分页显示：空格向前，b键向后
- `less filename`：和more类似，较more灵活
- `crontab -l`：查看定时执行的任务
- 查看 yum 下载后的包名：`rpm -qa | grep lrz`
- 查看文件路径，通常来说都会下载到路径`/usr/bin/`目录下：`rpm -ql lrzsz-0.12.20-36.el7.x86_64`
- 创建软连接，即快捷方式，创建order指向start.sh文件：`ln -s /junyi/start.sh /etc/ini.d/order`
- vim显示行号：`:set nu`


### 文档编辑
- `wc`显示文件的大小-c，显示行数-l



### 网络

显示全部运行中的网络IP、端口：`netstat -ano `

进一步筛选12455端口的情况：`netstat -ano | findstr 12455`

强制关闭pid为12345的进程：`taskkill /pid 12345 /f`



### 文件搜索

```shell
find [path] [expression] 

根据文件名搜索，严格搜索：find ./ -name today.log
不区分文件名大小写：find ./ -iname today.log
搜索文件大小大于100K的文件：find ./ -size +100K
搜索文件大小小于50K的文件：find ./ -size -50K
查找目录：-type d
查找文件：-type f
查找软链接：-type l
访问时间在5分钟以内：-amin -5
文件属性被更改在5分钟以内：-cmin -5
文件内容被修改：-mmin -5
```

### 上传下载

```shell
# 对于Uubuntu
sudo apt-get install lrzsz

# 对于Centos可以用下面的命令
sudo yum install lrzsz

# 上传
rz

# 下载
sz start.sh
```

### 开机自动启动

编辑`/etc/rc.d/rc.local`文件，在当中添加要执行的脚本，比如：

```shell
~/abc/start.sh &
/mnt/install.sh &
```



### 设置服务

```shell
1. 编写shell脚本，比如test.sh，内容如下：
#!/bin/bash
echo "haha"

2. 添加软链接，注意路径使用绝对路径
ln -s /home/jy/test.sh /etc/init.d/test

3. 然后就可以执行一下命令
service test
```





### 定时任务

- 查看定时任务：`crontab -e`
- 对于任务执行输出的内容，如果不想记录，那么在最后添加上：`> /dev/null 2>&1`

```shell
*    *    *    *    *
-    -   -    -   -
|    |    |    |    |
|    |    |    |    +----- 星期中星期几 (0 - 7) (星期天 为0)
|    |    |    +---------- 月份 (1 - 12) 
|    |    +--------------- 一个月中的第几天 (1 - 31)
|    +-------------------- 小时 (0 - 23)
+------------------------- 分钟 (0 - 59)

每一分钟执行一次 /bin/ls：
* * * * * /bin/ls

在 12 月内, 每天的早上 6 点到 12 点，每隔 3 个小时 0 分钟执行一次 /usr/bin/backup：
0 6-12/3 * 12 * /usr/bin/backup

周一到周五每天下午 5:00 寄一封信给 alex@domain.name：
0 17 * * 1-5 mail -s "hi" alex@domain.name < /tmp/maildata

每月每天的午夜 0 点 20 分, 2 点 20 分, 4 点 20 分....执行 echo "haha"：
20 0-23/2 * * * echo "haha"

下面再看看几个具体的例子：
0 */2 * * * /sbin/service httpd restart  意思是每两个小时重启一次apache 

50 7 * * * /sbin/service sshd start  意思是每天7：50开启ssh服务 

50 22 * * * /sbin/service sshd stop  意思是每天22：50关闭ssh服务 

0 0 1,15 * * fsck /home  每月1号和15号检查/home 磁盘 

1 * * * * /home/bruce/backup  每小时的第一分执行 /home/bruce/backup这个文件 

00 03 * * 1-5 find /home "*.xxx" -mtime +4 -exec rm {} \;  每周一至周五3点钟，在目录/home中，查找文件名为*.xxx的文件，并删除4天前的文件。

30 6 */10 * * ls  意思是每月的1、11、21、31日的6：30执行一次ls命令

@reboot(sleep90; sh \location\script.sh )
```

### alias

linux对一些命令设置了别名，常见的如下所示：

```shell
# 查看别名
> alias
alias cp='cp -i'
alias egrep='egrep --color=auto'
alias fgrep='fgrep --color=auto'
alias grep='grep --color=auto'
alias l.='ls -d .* --color=auto'
alias ll='ls -l --color=auto'
alias ls='ls --color=auto'
alias mv='mv -i'
alias rm='rm -i'
alias which='alias | /usr/bin/which --tty-only --read-alias --show-dot --show-tilde'

# 取消别名
unalias cp
```


### 时间校准





### 其他

- 程序执行常用命令：`nohup XXX > /dev/null 2>&1 &`。`nohup`使启动的程序不会受到SIGHUP信号的影响，即终端关闭后程序还可以继续执行。最后的`&`使程序让出前台终端，只使用`nohup`的话程序会占用前台终端。`> /dev/null 2>&1`表示将标准输出发送到特定的地方（类似于垃圾桶，表示不保存发送过来的信息，统统丢弃掉），`2>&1`表示将标准错误`2`发送到标准输出，即也是丢弃掉标准错误的输出
- 计算文件的MD5：`md5sum XXX`
- 生成公私钥：`ssh-keygen`



常用工具下载

```shell
–ftp服务
yum -y install ftp
–文本编辑
yum -y install vim
–下载及上传命令
yum -y install lrzsz
–打包
yum -y install zip unzip
- 网络工具
yum install net-tools
- yum工具
yum install -y yum-utils
```

s
