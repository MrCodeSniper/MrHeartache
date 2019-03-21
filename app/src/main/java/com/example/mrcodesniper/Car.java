package com.example.mrcodesniper;

public class Car { //类

    public void drive(){
        System.out.println("Driving a car!");
    }

}

class  Main{
    public static void main(String[] args){
        Car car=new Car(){
            @Override
            public void drive() {
                super.drive();
            }
        };

        //这里的car其实已经不算Car类了 算是Car的子类 因为名称于之前的类一样没有名字 所以称为 匿名子类 也叫匿名内部类
        //一个匿名内部类一定是在new的后面，用其隐含实现一个接口或实现一个类

    }
}

