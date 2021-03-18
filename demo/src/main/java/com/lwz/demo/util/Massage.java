package com.lwz.demo.util;

public class Massage {
    //0表示成功;-1表示失败
    int status;
    //向前端返回的内容
    String massage;
    public Massage() {
        super();
    }
    public Massage(int status, String massage) {
        super();
        this.status = status;
        this.massage = massage;
    }
    //get/set方法
}
