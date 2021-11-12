IDEA的建议设置：https://github.com/judasn/IntelliJ-IDEA-Tutorial/blob/master/settings-recommend-introduce.md





### 快捷键

- 查看类的子类或实现：Ctrl + H
- 多行注释：`Ctrl + Shift + /`
- 强制类型转换：'('后按`Ctrl + Shift + space`
- 隐藏run界面：`Shift + ESC`
- 显示函数的参数：在括号内按`Ctrl + P`
- 根据语句的返回值自动创建变量：`Ctrl + Alt + v`
- 查找当前类中的方法：`Ctrl + F12`
- 在项目进行文本查找: `Ctrl + Shift + F`
- 查找Class: `Ctrl + N`
- 查找文件: `Ctrl + Shift + N`
- 查找symbol: `Ctrl + Shift + Alt + N`
- 跳转到指定行: `Ctrl + G`
- 跳转子Tab: `Ctrl + 左右`
- 跳转当前文件中的方法: `Ctrl + 上下`
- 快速关闭Tab: `Shift + 鼠标左键`
- 快速跳转到 Project：`Alt + 1`
    - 跳回代码块：`Esc`或者`F4`
- 移动分隔栏：`Ctrl + Shift + 左右`
- 代码自动收尾，例如添加分号，添加大括号等：`Ctrl + Shift + Enter`
- 列编辑：Alt + 鼠标拖动


### Live Temple
- for循环：`itar`

### Inject language
快速创建特定语言的变量数据，例如创建json格式的字符串变量，能够帮我们自动进行转义

**使用方法**

先将焦点定位到双引号里面，使用alt+enter快捷键弹出inject language视图，并选中Inject language or reference。

选择后,切记，要直接按下enter回车键，才能弹出inject language列表。在列表中选择 json组件。

再次使用alt+enter就可以看到，选中Edit JSON Fragment并回车，就可以看到编辑JSON文件的视图了

使用 Ctrl + F4 退出

### 关闭Duplicate code
关闭该提示；File → Settings → Editor → Inspections；在Settings页面右侧的搜索栏处搜索 “Duplicated Code”，取消掉Duplicated Code后面的勾选，再保存设置即可：

### 自动构建
当修改代码之后都需要重新启动项目，但是对于启动项目需要耗费时间。Spring Boot 官方推荐的 Developer Tool，可以让我们修改代码后不用重新启动项目

1. 进入 Settings (Preferences on macOS)。
2. 打开 Build, Execution, Deployment > Compiler. 启用 Build project automatically。点击 应用。
3. 按 Ctrl+Shift+A (Cmd+Shift+A on macOS)快捷键，然后搜索 Registry。打开之后找到 compiler.automake.allow.when.app.running，并启用它 (IntelliJ IDEA 15 and newer)。

### 代码模板
Settings中的File and Code Template，先在选项卡Include配置好模板my.java，然后在Files选项卡中选择文件添加模板`#parse("my.java")`


### 添加右键文件背景打开IDEA
1. 打开注册表：`regedit`
2. 展开`计算机\HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Directory\background\shell\`
3. 添加IDEA文件夹，并且添加子文件夹command
4. IDEA属性配置：
    - (默认)：Open in IDEA
    - Icon：F:\IDE\IntelliJ IDEA Community Edition 2020.1.2\bin\idea64.exe
5. command文件夹属性配置：
    - (默认)："F:\IDE\IntelliJ IDEA 2020.3.1\bin\idea64.exe" "%V"

### 远程调试
1. Add Configuration
2. 添加“Remote”
3. 配置Host和Port
4. 在远程服务器中添加启动参数，如`java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar application.jar`

- transport：监听Socket端口连接方式,常用的dt_socket表示使用socket连接.
- server：=y表示当前是调试服务端，=n表示当前是调试客户端；
- suspend：=n表示启动时不中断.
- address：=5005表示本地监听5005端口。

5. 远程服务器防火墙端口放行
```shell
### sudo vim /etc/sysconfig/iptables
-A INPUT -m state --state NEW -m tcp -p tcp --dport 5005 -j ACCEPT
### 重启生效: sudo systemctl restart iptables
```



### 好用的IDEA plugin
- GsonFormat：一键根据JSON生成java类，快捷键：Alt+S
- Maven Helper：查看Maven依赖
- Rainbow brackets：彩虹颜色的括号
- VisualVM Launcher：运行java程序的时候，启动VisualVM，方便查看jvm的情况
- SequenceDiagram：能够生成方法调用的时序图。通过右键点击方法名称，在弹出的选项框中选择"sequence Diagram"
- Statistic: 统计代码行数
- RestfulTookit: 
    - 快速跳转到Refu API处, 快捷键: `Ctrl + Alt + N`
    - 右键点击Controller中的方法, 可以生成URL, 请求参数的JSON形式等
    - 右键点击Domain的类, 可以生成JSON格式的对应数据
- MyBatis Log Plugin: 把MyBatis输出的日志装配成完整的请求语句，方便直接复制到mysql中直接执行
- Free MyBatis：支持.xml文件和Mapper的Java接口文件之间的跳转
- MyBatisCodeHelperPro ：MyBatis插件
- Material Theme UI：更多的主题
- Properties to YAML Converter
    - 直接右键properties文件，在菜单中选择"Convert Properties to YAML"
- Alibaba Cloud Toolkit：快速部署程序到服务器
- Json Parser：IDEA的Json格式化工具
- Translation ：翻译插件