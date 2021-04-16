package com.junyi;

/**
 * @time: 2021/3/26 11:51
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Remote {
    private CallBackMy callBack;

    public void setCallBack(CallBackMy callBack) {
        this.callBack = callBack;
    }

    public void execute() {
        System.out.println("执行任务");
        callBack.handle("message body");
    }

}
