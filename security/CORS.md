### CORS

跨域资源共享(Cross-origin resource sharing, CORS)

首先看一下下面这个场景：

用户访问了 `www.a.com` 网站，`www.a.com`生成用户的Cookie信息，并且发送给浏览器，以后在访问 `www.a.com`网站的时候，浏览器都会自动将Cookie发送给 `www.a.com`，如果Cookie正确，那么无需再验证用户的身份信息，实现热登陆。

但是，当用户访问了一个不安全的网站`www.b.com`，浏览器在加载`www.b.com`的JavaScript脚本的时候，发送请求给`www.a.com`网站，如果浏览器保存了Ccookie信息，那么会导致直接登录`www.a.com`成功，因此不安全的网站`www.b.com`能够获得该用户在`www.a.com`上面的个人信息等等

为了避免这种不安全的行为，浏览器进行了同源策略的限制，JavaScript中只能发送请求给相同来源的网站，比如www.a.com中的JavaScript要访问www.b.com的资源，一般是拒绝的。

但是这对于RestFul应用来说，很多时候页面的JavaScript需要调用多个域名的RestFul API接口，由于同源策略的限制，将无法访问。

而CORS能够支持跨域访问





### 设置CORS的方法
- 使用@CrossOrigin
- 使用CorsRegistry

下面配置的都是只允许来自`local.liaoxuefeng.com`的访问

#### 使用@CrossOrigin
```java
@CrossOrigin(origins = "http://local.liaoxuefeng.com:8080")
@RestController
@RequestMapping("/api")
public class ApiController {
    ...
}
```

#### 使用CorsRegistry
能够集中展示CORS，推荐使用
```java
@Bean
WebMvcConfigurer createWebMvcConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("http://local.liaoxuefeng.com:8080")
                    .allowedMethods("GET", "POST")
                    .maxAge(3600);
            // 可以继续添加其他URL规则:
            // registry.addMapping("/rest/v2/**")...
        }
    };
}
```

