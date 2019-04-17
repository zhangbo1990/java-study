package com.zhangbo.java.test.syn;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangbo
 * @Date: 2019/4/17
 * @Description:
 * @Modified By:
 **/
public class UnSynchBank {
    //创建可重入锁 入参决定是否使用公平获取锁的机制
    private Lock lock = new ReentrantLock();
    private double[] account;

    public UnSynchBank(int size, double initMoney) {
        account = new double[size];
        Arrays.fill(account, initMoney);
    }

    public void transfer(int from, int to, double transferMoney) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName());
            if (account.length < from || account.length < to) {
                System.out.println("不存在账户");
                return;
            }
            if (account[from] < transferMoney) {
                System.out.println("该账户余额不足");
                return;
            }
            account[to] += transferMoney;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account[from] -= transferMoney;
            System.out.printf("%d account transfer %d account %10.2f%n", from, to, transferMoney);
            System.out.printf("total balance %10.2f%n", getTotalMoney());

        }catch (Exception e){

        }
        finally {
            lock.unlock();
        }

    }

    public double getTotalMoney() {
        double total = 0;
        for (double m : account) {
            total += m;
        }
        return total;
    }

    public int getSize() {
        return account.length;
    }

}
