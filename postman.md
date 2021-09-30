### postman



环境变量和Collection中的Variables都可以通过`{{XXX}}`的方式来进行引用

Pre-request Script是在请求语句执行前执行Script脚本

Tests是在语句执行后执行相应的Script脚本



```shell
# 设置全局变量
pm.globals.set("key1","value1");
# 设置环境变量
pm.environment.set("key2","value2");
# 设置 collection 中的 variable 变量
pm.collectionVariables.set("key3","value3");
# 删除变量
pm.collectionVariables.unset("key3");
```



### 发送get请求

```javascript
pm.sendRequest("127.0.0.1:8000/api/test", function(err, response) {
    console.log("data:" + response.json().data);
});
```



### 发送Post请求

```javascript
const loginRequest = {
    url: 'http://127.8.0.1:8080/toLogin",
    method :"POST",
    body : {
        mode: "urlencoded ',
        urlencoded : "username=hydra&password=123456”        
    }
);
pm.sendRequest( loginRequest, function (err,response) {
    console.log(response.json( ).data) ;
    pm.collectionVariables.set("TOKEN" ,response.json().data.token);   
});


// 传递json参数
body: {
  mode: 'raw',
  raw: JSON.stringify({ key: 'value' })
}
```



### 在Tests中调用登陆接口后，设置TOKEN变量

```javascript
pm.collectionVariables.set("TOKEN", pm.response.json().data.token);
```

