package com.wesmarclothing.jniproject;

import org.junit.Test;

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName CodeOrderJava
 * @Date 2019/8/22 14:36
 * @Author JACK
 * @Describe
 * @Project JNIProject
 */
public class CodeOrderJava extends Test3 {


    String name = "CodeOrderJava";


    /**
     * 静态代码块和静态成员变量统计执行顺序由代码顺序决定
     * 只会在类第一次加载的时候最先加载进方法区
     */
    static {
        System.out.println("静态代码块");
    }

    static Test2 test2 = new Test2("静态成员变量");


    /**
     * 构造器代码块和非静态成员变量同级，执行顺序由代码顺序决定，
     * 会在每次类新建时执行一次，在静态代码块之后，构造器函数之前
     */
    Test2 test1 = new Test2("非静态成员变量");

    {
        System.out.println("构造器代码块");
    }


    public CodeOrderJava() {

        System.out.println("构造器函数");
    }

    /**
     * 父类：静态代码块
     * 静态代码块
     * 测试类：Test2:静态成员变量
     * 父类：构造器代码块
     * 父类：Test3：构造器
     * 测试类：Test2:非静态成员变量
     * 构造器代码块
     * 构造器函数
     * 测试类：CodeOrderJava
     * 父类：构造器代码块
     * 父类：Test3：构造器
     * 测试类：Test2:非静态成员变量
     * 构造器代码块
     * 构造器函数
     */
    @Test
    public void name() {
        /**
         * 普通类的执行顺序是：静态代码块或静态成员变量》构造器代码块或者非静态成员变量》构造器函数
         *
         * 有父类的情况下：父类静态属性》子类的静态属性》父类的构造代码块或属性》父类的构造器》子类的构造代码块或属性》类构造器
         */
        System.out.println("测试类：CodeOrderJava");
        CodeOrderJava java = new CodeOrderJava();

//        System.out.println("测试类：CodeOrderJava------------：2");
//        CodeOrderJava java2 = new CodeOrderJava();
    }


}


class Test2 {
    public Test2(String name) {
        System.out.println("测试类：Test2:" + name);
    }
}

class Test3 {

    {
        System.out.println("父类：构造器代码块");
    }

    static {
        System.out.println("父类：静态代码块");
    }

    public Test3() {
        System.out.println("父类：Test3：构造器");
    }
}
