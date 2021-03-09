package com.albert.concurrent.book.chapterfour;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Albert
 * @date 2021/3/9 下午6:24
 */
public class AtomicReference_03 {


    static AtomicReference<Integer> personMoney = new AtomicReference<>();

    static {
        personMoney.set(19);
    }

    /**
     * 购买
     */
    public static void buy() {
        while (true){
            int money = personMoney.get();
            if(money>20){
                System.out.println("开始消费");
                personMoney.compareAndSet(money,money-20);
            }
        }
    }

    /**
     * 充值，只允许充值一次。（会产生ABA问题）
     */
    public static void recharge() {
        Integer money = personMoney.get();
        while (true) {
            if (money < 20) {
                boolean b = personMoney.compareAndSet(money, money+20);
                if (b) {
                    System.out.println("充值成功");
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            recharge();
        }).start();
        new Thread(()->{
            buy();
        }).start();
        System.out.println(personMoney.get());
    }


}
