
```
以下的例子使用的测试文件test.txt，内容如下：
root:x:0:0:root:/root:/usr/bin/zsh
daemon: x:1:1:daemon :/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sync:x:4:65534:sync:/bin:/bin/sync
```

### 内置变量
- $0代表了一行,$1表示第一个字段,$2表示第二个字段
- NR：表示当前处理的是第几行
- NF：表示当前行有多少个字段
- FILENAME：当前文件名
- FS：字段分隔符，默认是空格和制表符。
- RS：行分隔符，用于分割每一行，默认是换行符。
- OFS：输出字段的分隔符，用于打印时分隔字段，默认为空格。
- ORS：输出记录的分隔符，用于打印时分隔记录，默认为换行符。

```
# 指定空格、冒号、tab 作为分隔符
awk -F'[:\t ]' '{print $1, $3}' test.txt
```

```
# 分隔符为':'，读取每行分隔后的第二个字段
awk -F ':' '{print $2}' test.txt
awk -F: '{print $2}' test.txt
x
 x
x
x
x
```
```
awk -F ':' '{print $1,  $(NF-1)}' test.txt
查看每行第一个字段和倒数第二个字段
root /root
daemon /usr/sbin
bin /bin
sys /dev
sync /bin
```
```
awk -F ':' '{print NR ")" $1}' test.txt
1)root
2)daemon
3)bin
4)sys
5)sync
```
```
awk -v OFS="->" -F: '{print $1, $2, $3}' test.txt
分隔符为‘：’，字段之间的连接默认是空格，这儿设置为"->"
root->x->0
daemon-> x->1
bin->x->2
sys->x->3
sync->x->4
```


### 条件限制
```
awk -F ':' 'NR % 2 == 1 {print $1}' test.txt
展示奇数行
root
bin
sync
```
```
awk -F ':' 'NR >3 {print $1}' test.txt
sys
sync
```
```
awk -F ':' '$1 == "root" {print $1}' test.txt
root
```
```
awk -F ':' '{if($1 > "q") print $1; else print "--"}' test.txt
root
--
--
sys
sync
```

### 函数
- tolower()：字符转为小写。
- toupper()：字符转为大写
- length()：返回字符串长度。
- substr()：返回子字符串。
- sqrt()：平方根。
- rand()：随机数
```
awk -F ':' '{print length($1)}' test.txt
4
6
3
3
4
```
```
awk -F ':' '{print toupper($1)}' test.txt
ROOT
DAEMON
BIN
SYS
SYNC
```

