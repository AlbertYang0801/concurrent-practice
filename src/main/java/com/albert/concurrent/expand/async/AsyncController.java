package com.albert.concurrent.expand.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试异步
 * @author yangjunwei
 * @date 2021/7/7 4:28 下午
 */
@RestController
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("/asyncTest")
    public String testAsync(){
        asyncService.startScript(10);
        return "执行结果返回";
    }


}
