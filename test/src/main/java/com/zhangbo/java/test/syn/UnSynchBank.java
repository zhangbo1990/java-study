package com.zhangbo.java.test.syn;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
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
    private Lock lock;
    private double[] account;
    private Condition sufficientFunds;

    public UnSynchBank(int size, double initMoney) {
        account = new double[size];
        Arrays.fill(account, initMoney);
        lock = new ReentrantLock();
        sufficientFunds = lock.newCondition();
    }

    public void transfer(int from, int to, double transferMoney) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName());
            if (account.length < from || account.length < to) {
                System.out.println("不存在账户");
                return;
            }
            while(account[from] < transferMoney) {
                System.out.println("该账户余额不足等待。。。。");
                sufficientFunds.await();
            }
            account[to] += transferMoney;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account[from] -= transferMoney;
            sufficientFunds.signalAll();
            System.out.printf("%d account transfer %d account %10.2f%n", from, to, transferMoney);
            System.out.printf("total balance %10.2f%n", getTotalMoney());

        }catch (Exception e){

        }
        finally {
            lock.unlock();
        }

    }

    public double getTotalMoney() {
        lock.lock();
        double total = 0;
        try {

            for (double m : account) {
                total += m;
            }
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
        return total;
    }

    public int getSize() {
        return account.length;
    }

}
