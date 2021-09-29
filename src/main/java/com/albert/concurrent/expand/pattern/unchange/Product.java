package com.albert.concurrent.expand.pattern.unchange;

/**
 * 不变模式-不变对象
 * final修饰的类不能被继承
 * @author yjw
 * @date 2021/9/25 22:41
 */
public final class Product {

    //属性私有且final修饰
    private final String no;
    private final String name;
    private final double price;

    //创建完整对象的构造方法
    public Product(String no, String name, double price){
        super();
        this.no=no;
        this.name=name;
        this.price=price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
