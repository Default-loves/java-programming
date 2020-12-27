

### Git Auto pull
```bash
cd Java-Programming
git pull;
cd ../
cd Learn-Spring
git pull;
cd ../
cd java-pit
git pull;
cd ../
cd LeetCodeMy
git pull;
cd ../
cd MyBatisLearning
git pull;
cd ../
cd DesignPattern
git pull;
cd ../

```


```bash
git add .
git commit -m "update"
git push


```

### 一些搜索技巧
- awesome  [keyword]
- in:name  [keyword]，仓库名称
- in:readme  [keyword]
- in:description  [keyword]
- stars:>NUMBER [keyword]
- size:>NUMBER [keyword]
- pushed:>2019-01-03 [keyword]
- created:>2019-01-03 [keyword]
- language:LANGUAGE [keyword]
- license:apache-2.0 [keyword]
- user: [name]

in:name/description/readme XXX stars:>1000 pushed:>2019-10-01 language:java


### GitHub操作
- 切换到指定的分支(如：develop分支)：`git checkout develop`
- 在当前分之下创建新的分支：`git branch develop-A`
- 添加新写的代码：`git add feature-A`
- 添加文件夹下面的所有文件：`git add .`
- 提交新写的代码：`git commit -m "add feature A"`
- 获取远程仓库的最新更新的分支：`git pull origin develop`
- 合并分支：`git merge --no-ff feature-A`
- 分支推送到远程仓库：`git push origin develop`
- 把本地库与远程库关联：`git remote add origin HTTPS://XXX`
- 查看历史提交记录：`git log`
- 回退版本：`git reset --hard ID`
- 永久记住GitHub的账号和密码：`git config --global credential.helper store`



![img](https://mmbiz.qpic.cn/mmbiz_png/I0OrH0ZH2Kp9Ziaia9ClG4ZFF15BKWnCdu5RHAtZDmZw5VZxxtSjopLuUbG4ILDmSib89A8Zqo4oApmJyLnbOWTtg/640?tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 自动同步Gitee仓库和GitHub仓库
添加Gitee仓库地址到Github项目，将提交发送到所有的仓库中
1. 在Github项目下通过git添加Gitee地址：`git remote set-url --add origin repositoryUrl`
2. 查看项目的所有远程仓库地址：`git remote -v`
3. 可以通过`push`将代码同步到所有的远程仓库: `git push`


### Question
缓冲区溢出：`git config http.postBuffer 524288000`

### 保存账号密码
打开终端
执行 vim ～/.git-credentials
执行i进入编辑状态
输入 https://{username}:{password}@github.com
或者 https://{username}:{password}@gitlab.com
Esc -> :wq保存并退出
然后执行:git config --global credential.helper store



### 使用的 config 文件
```shell
[core]
	repositoryformatversion = 0
	filemode = false
	bare = false
	logallrefupdates = true
	symlinks = false
	ignorecase = true
[remote "origin"]
	url = https://gitee.com/xujunyiTK/java-programming.git
	url = https://github.com/Default-loves/java-programming.git
	fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
	remote = origin
	merge = refs/heads/master
[http]
	postBuffer = 524288000

```