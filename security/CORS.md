### CORS

跨域资源共享(Cross-origin resource sharing, CORS)

首先看一下下面这个场景：

用户访问了 `www.a.com` 网站，`www.a.com`生成用户的Cookie信息，并且发送给浏览器，以后在访问 `www.a.com`网站的时候，浏览器都会自动将Cookie发送给 `www.a.com`，如果Cookie正确，那么无需再验证用户的身份信息，实现热登陆。

但是，当用户访问了一个不安全的网站`www.b.com`，浏览器在加载`www.b.com`的JavaScript脚本的时候，发送请求给`www.a.com`网站，如果浏览器保存了Ccookie信息，那么会导致直接登录`www.a.com`成功，因此不安全的网站`www.b.com`能够获得该用户在`www.a.com`上面的个人信息等等

为了避免这种不安全的行为，浏览器进行了同源策略的限制，JavaScript中只能发送请求给相同来源的网站，比如www.a.com中的JavaScript要访问www.b.com的资源，一般是拒绝的。

但是这对于RestFul应用来说，很多时候页面的JavaScript需要调用多个域名的RestFul API接口，由于同源策略的限制，将无法访问。

而CORS能够支持跨域访问





### Spring中设置CORS的方法
- 使用CorsRegistry
- 使用Filter
- 使用@CrossOrigin

下面配置的都是只允许来自`local.liaoxuefeng.com`的访问

#### 使用CorsRegistry

能够集中展示CORS，推荐使用

```java
@Bean
WebMvcConfigurer createWebMvcConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("http://local.liaoxuefeng.com:8080")	// 只允许指定来源访问
//		                 .allowedOrigins("*")	// 允许任意来源访问
                    .allowedMethods("GET", "POST")
                		 .allowCredentials(true)
                    .maxAge(3600)
                    .allowedHeaders("*");
            // 可以继续添加其他URL规则:
            // registry.addMapping("/rest/v2/**")...
        }
    };
}
```

#### 使用Filter

拦截请求处理后的响应，添加相应的 Header

```java
@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }
}
```

#### 使用@CrossOrigin

能够细粒度针对单个 Restful 方法进行控制

```java
@CrossOrigin(origins = "http://local.liaoxuefeng.com:8080")
@RestController
@RequestMapping("/api")
public class ApiController {
    ...
}
```



#### 响应头中的字段说明

- Access-Control-Allow-Origin 该字段必填。它的值要么是请求时Origin字段的具体值，要么是一个*，表示接受任意域名的请求。
- Access-Control-Allow-Methods 该字段必填。它的值是逗号分隔的一个具体的字符串或者*，表明服务器支持的所有跨域请求的方法。注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。
- Access-Control-Expose-Headers 该字段可选。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。
- Access-Control-Allow-Credentials 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie.默认情况下，不发生Cookie，即：false。对服务器有特殊要求的请求，比如请求方法是PUT或DELETE，或者Content-Type字段的类型是application/json，这个值只能设为true。如果服务器不要浏览器发送Cookie，删除该字段即可。
- Access-Control-Max-Age 该字段可选，用来指定本次预检请求的有效期，单位为秒。在有效期间，不用发出另一条预检请求。