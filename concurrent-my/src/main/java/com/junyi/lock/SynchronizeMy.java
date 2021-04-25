package com.junyi.lock;

/**
 * 所用锁的正确姿势
 *
 * 使用锁的时候一定要注意资源是用的什么锁，通常来说我们使用细粒度锁，即不同的资源用不同的锁来保护
 * 如果同时存在多个资源，比如这个例子，这儿使用的锁是账户A对象的锁，我们使用A账户的锁来锁A的账户和B的账户，B账户还是可以被其他线程访问
 * 比如A转账给B，B转账给C，就存在问题
 * 因此我们使用粒度更大的锁，使用Account类来锁资源，就可以了，影响就是所有的转账操作都是串行的，性能很低
 *
 * 性能更好的做法是转账的时候获取两个锁，一个是转入账号的锁，一个是转出账号的锁，只有都获取到了才能进行转账，这样允许多个转账记录并行执行
 * @time: 2021/4/22 15:40
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class SynchronizeMy {

    class Account{
        private Integer money;

        /** 转账错误的代码 */
        public synchronized void tranferWrong(Account target, Integer amount) {
            if (this.money > amount) {
                this.money -= amount;
                target.money += amount;
            }
        }

        /** 转账正确的代码 */
        public void tranferRight(Account target, Integer amount) {
            synchronized (Account.class) {
                if (this.money > amount) {
                    this.money -= amount;
                    target.money += amount;
                }
            }
        }

        /** 转账正确的代码，性能更好 */
        public void tranferRightAndBetter(Account target, Integer amount) {
            synchronized (this) {
                synchronized (target) {
                    if (this.money > amount) {
                        this.money -= amount;
                        target.money += amount;
                    }
                }
            }
        }
    }
}
