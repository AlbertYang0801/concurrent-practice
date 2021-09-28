package com.albert.concurrent.expand.pattern.jdkfuture;

import java.util.concurrent.Callable;

/**
 * JDK提供的Future模式
 * 异步计算获取结果
 *
 * @author yangjunwei
 * @date 2021/9/28 3:03 下午
 */
public class RealData implements Callable<String> {
    private String content;

    public RealData(String content) {
        this.content = content;
    }

    @Override
    public String call() throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(content);
            Thread.sleep(1000);
        }
        return stringBuffer.toString();
    }
}
