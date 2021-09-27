package com.albert.concurrent.expand.pattern.future;

/**
 * 真实数据模型
 *
 * @author yangjunwei
 * @date 2021/9/27 10:34 下午
 */
public class RealData implements Data {

    protected String result;

    public RealData(String result) {
        //RealData的构造很慢
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(result);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.result = stringBuffer.toString();
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RealData{" +
                "result='" + result + '\'' +
                '}';
    }

    public static void main(String[] args) {
        RealData realData = new RealData("hello");
        System.out.println(realData.toString());
    }


}
