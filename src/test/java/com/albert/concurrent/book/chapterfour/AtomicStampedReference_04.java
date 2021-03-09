package com.albert.concurrent.book.chapterfour;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Albert
 * @date 2021/3/9 下午6:38
 */
public class AtomicStampedReference_04 {


    static AtomicStampedReference<Integer> personMoney = new AtomicStampedReference<>(19,10);

    /**
     * 购买
     */
    public static void buy() {
        while (true){
            int money = personMoney.getReference();
            int stamp = personMoney.getStamp();
            if(money>20){
                System.out.println("开始消费");
                personMoney.compareAndSet(money,money-20,stamp,stamp+1);
            }
        }
    }

    /**
     * 充值，只允许充值一次。（使用AtomicStampedReference可解决ABA问题）
     */
    public static void recharge() {
        Integer money = personMoney.getReference();
        int stamp = personMoney.getStamp();
        System.out.println(money);
        while (true) {
            if (money < 20) {
                boolean b = personMoney.compareAndSet(money, money+20,stamp,stamp+1);
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
        System.out.println(personMoney.getReference());
    }


}
