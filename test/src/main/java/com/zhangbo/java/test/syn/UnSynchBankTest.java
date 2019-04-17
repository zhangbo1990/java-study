package com.zhangbo.java.test.syn;

/**
 * @Author: zhangbo
 * @Date: 2019/4/17
 * @Description:
 * @Modified By:
 **/
public class UnSynchBankTest {
    private final static int ACCOUNT_SIZE = 1000;
    private final static double INIT_MONEY = 1000;
    private final static int DELAY = 10;
    public static void main(String[] args) {
        UnSynchBank bank = new UnSynchBank(ACCOUNT_SIZE, INIT_MONEY);
        for(int i = 0; i < ACCOUNT_SIZE; i++){
            int from = i;
            Thread thread = new Thread(() -> {
                while(true){
                    int to = (int)(bank.getSize()*Math.random());
                    double transferMoney = INIT_MONEY*Math.random();
                    bank.transfer(from,to,transferMoney);
                    try {
                        Thread.sleep((int)(DELAY* Math.random()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            thread.start();
        }
    }


}
