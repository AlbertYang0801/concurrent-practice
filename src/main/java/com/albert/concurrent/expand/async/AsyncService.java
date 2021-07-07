package com.albert.concurrent.expand.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author yangjunwei
 * @date 2021/7/7 4:28 下午
 */
@Service
public class AsyncService {

    @Async
    public void startScript(int page){
        System.out.println(page);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行结束");
    }



}
