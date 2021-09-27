package com.albert.concurrent.expand.pattern.future;

import lombok.SneakyThrows;

/**
 * @author yangjunwei
 * @date 2021/9/27 10:42 下午
 */
public class Client {

    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();
        //开启线程，注入对象
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }.start();
        //直接返回代理对象
        return futureData;
    }

    @SneakyThrows
    public static void main(String[] args) {
        Client client = new Client();
        Data hello = client.request("hello");
        System.out.println("发起请求");
        Thread.sleep(2000);
        System.out.println("请求结果阻塞");
        System.out.println(hello.getResult());
    }


}
