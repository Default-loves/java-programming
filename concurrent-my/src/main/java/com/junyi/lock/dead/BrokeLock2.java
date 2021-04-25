package com.junyi.lock.dead;

/**
 *  避免死锁
 *  按照固定的顺序从小到大加锁
 * @time: 2021/4/22 17:29
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class BrokeLock2 {

    class Account{
        private Integer id;     // 账户的编号，我们按照这个从小到大的顺序加锁
        private Integer money;
        public void tranferRightAndBetter(Account target, Integer amount) {
            Account left = this;
            Account right = target;
            if (target.id < this.id) {
                left = target;
                right = this;
            }
            synchronized (left) {
                synchronized (right) {
                    if (this.money > amount) {
                        this.money -= amount;
                        target.money += amount;
                    }
                }
            }
        }
    }
}
