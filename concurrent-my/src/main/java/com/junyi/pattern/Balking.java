package com.junyi.pattern;

/**
 * Balking 模式
 * 常用场景：服务依赖于一个共享的状态变量，当状态变量满足某个条件的时候，执行指定的代码逻辑
 * @time: 2021/4/16 15:22
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Balking {

    boolean changed=false;  // 共享的状态变量

    //自动存盘操作
    void autoSave(){
        synchronized(this){
            if (!changed) {
                return;
            }
            changed = false;
        }
        //执行存盘操作
        //省略且实现
    }

    //编辑操作
    void edit(){
        //编辑逻辑
        change();
    }

    //改变状态，单独将这个并发操作提取出来，和业务逻辑分隔开
    void change(){
        synchronized(this){
            changed = true;
        }
    }
}
