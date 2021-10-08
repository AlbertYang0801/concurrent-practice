package com.albert.concurrent.expand.pattern.future;

/**
 * @author yangjunwei
 * @date 2021/9/27 10:33 下午
 */
public class FutureData implements Data {

    private RealData realData = null;

    private volatile boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        //无需重复注入
        if(isReady){
            return;
        }
        this.realData = realData;
        isReady=true;
        //注入成功，唤醒
        notifyAll();
    }

    @Override
    public synchronized String getResult(){
        while (!isReady){
            try {
                //未注入，等待
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return realData.result;
    }


}
