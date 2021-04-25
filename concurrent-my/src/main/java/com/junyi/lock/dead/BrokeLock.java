package com.junyi.lock.dead;

import com.junyi.lock.SynchronizeMy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 避免死锁
 * 使用一个单例对象来一次性申请所有的资源
 * @time: 2021/4/22 17:14
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class BrokeLock {

    /** 单例对象 */
    class AccountMaster {
        private List<Account> inUse = new ArrayList<>();

        /** 申请资源 */
        public synchronized boolean acquire(Account a, Account b) throws InterruptedException {
            while (inUse.contains(a) || inUse.contains(b)) {
                wait();
            }
            inUse.add(a);
            inUse.add(b);
            return true;
        }
        /** 释放资源 */
        public synchronized boolean release(Account a, Account b) {
            inUse.remove(a);
            inUse.remove(b);
            notifyAll();
            return true;
        }
    }


    class Account {
        private Integer money;
        private BrokeLock.AccountMaster accountMaster;

        public Account(BrokeLock.AccountMaster master, Integer money) {
            this.accountMaster = master;
            this.money = money;
        }

        public void tranferRightAndBetter(Account target, Integer amount) {
            try {
                accountMaster.acquire(this, target);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                synchronized (this) {
                    synchronized (target) {
                        if (this.money > amount) {
                            this.money -= amount;
                            target.money += amount;
                        }
                    }
                }
            } finally {
                accountMaster.release(this, target);
            }
        }
    }

    @Test
    public void test() {
        AccountMaster master = new AccountMaster();
        Account a = new Account(master, 100);
        Account b = new Account(master, 200);
        a.tranferRightAndBetter(b, 50);
        System.out.println(a.money);
        System.out.println(b.money);

    }
}
