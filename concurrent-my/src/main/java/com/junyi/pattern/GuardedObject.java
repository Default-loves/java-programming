package com.junyi.pattern;

import com.junyi.entity.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * 设计模式：Guarded Suspension(保护性暂停)，是一种等待唤醒机制的实现，也叫做“多线程版本的if”
 * <p/>
 * 常用场景：thread1发送消息，阻塞等待响应，thread2等待消息的接收，消息到达后唤醒thread1线程执行后续操作
 * @time: 2021/3/30 11:06
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class GuardedObject<T>{
    //受保护的对象
    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout=2;
    //保存所有GuardedObject，Key：message id  Value: GuardedObject
    final static Map<Object, GuardedObject> gos=new ConcurrentHashMap<>();

    //静态方法创建GuardedObject
    static <K> GuardedObject create(K key){
        GuardedObject go=new GuardedObject();
        gos.put(key, go);
        return go;
    }

    static <K, T> void fireEvent(K key, T obj){
        GuardedObject go=gos.remove(key);
        if (go != null){
            go.onChanged(obj);
        }
    }

    //获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法
            while(!p.test(obj)){
                System.out.println("等待中。。。");
                done.await(timeout, TimeUnit.SECONDS);
            }
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }
    //事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //处理浏览器发来的请求
    public void handleWebReq(){
        String id= UUID.randomUUID().toString();
        Message msg1 = new Message(id,"message abc");
        //创建GuardedObject实例
        GuardedObject<Message> go= GuardedObject.create(id);
        //发送消息
        send(msg1);

        //等待MQ消息
        Message r = go.get(t -> t != null);
        System.out.println("接收到消息：" + r.toString());
    }

    private void send(Message msg) {
        try {
            // 发送消息
            System.out.println("发送消息完毕，阻塞中。。。");
            Thread.sleep(3_000);
            onMessage(new Message(null, "I'm response message"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void onMessage(Message msg){
        System.out.println("收到消息：" + msg.toString());
        //唤醒等待的线程
        GuardedObject.fireEvent(msg.getId(), msg);
    }

    @Test
    public void test() {
        handleWebReq();
    }
}