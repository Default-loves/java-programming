- `wget http:XXX`下载文件
- `mv FILE/DIR destFILE/destDIR`：改名
- `tar -zxvf XXX.tar.gz`
- mkdir -p data/{0..2}
- man COMMAND
- `cp [option] source dest`
    - -u：只更新不一样的
    - -v：显示操作
    - -r：对于文件夹，迭代复制
- `cp --force --backup=numbered a.txt a.txt`：设置备份
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


### 文档编辑
- `wc`显示文件的大小-c，显示行数-l



### 网络

显示全部运行中的网络IP、端口：`netstat -ano `

进一步筛选12455端口的情况：`netstat -ano | findstr 12455`

强制关闭pid为12345的进程：`taskkill /pid 12345 /f`