

类A中字段是`String`类型，内容为JSON，那么如果对类A进行序列化后，会导致字段`jsonStr`添加很多无用的斜杠`\`，解决方法如下：

```java

import com.alibaba.fastjson.annotation.JSONField;
class A{
    @JSONField(jsonDirect=true)
    public String jsonStr;
     ...
}

// 或者
import com.fasterxml.jackson.annotation.JsonRawValue;
class A{
    @JsonRawValue
    public String jsonStr;
     ...
}
```

配置字段序列化后的顺序，顺序随着值从小到大：`@JSONField(ordinal = 99)`

日期字段配置格式：`@JSONField(format="yyyyMMdd")`

