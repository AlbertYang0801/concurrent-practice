package com.albert.concurrent.expand.pattern.producermodel;

/**
 * 不变模式-对象
 * @author yangjunwei
 * @date 2021/9/27 9:53 下午
 */
public final class PCData {

    private final int intData;

    public PCData(int intData) {
        this.intData = intData;
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
