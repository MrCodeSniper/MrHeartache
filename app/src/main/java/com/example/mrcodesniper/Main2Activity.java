package com.example.mrcodesniper;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    private TestViewModel testViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //初始化viewmodel
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

        //创建数据观察者
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //activity不可见 不会接受到
                Log.d("xxx","接收到事件"+s);
            }
        };

        //数据的观察与生命周期挂钩  加入观察者
        testViewModel.getLiveData().observe(this,observer);

        LiveDataBus.get().with("eventName",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("xxx","bus:"+s);
            }
        });

    }


    //匿名内部类 持有外部类activity的引用 所以执行耗时任务时activity不可见后生命周期是执行的 但还是被引用 无法被回收 所以内存泄漏
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("xxx","收到消息");
        }
    };

    public void handler(View view){
        handler.sendEmptyMessageDelayed(0,2000);
    }


    public void handler2(View view){
        postEvent();
    }


    public void handler3(View view){
        //注册
        LiveDataBus.get().with("eventName",String.class).postValue("发送组件内事件");
    }

    public void handler4(View view){
        LiveDataBus.get().with("key_test",String.class).setValue("发送给未启动组件事件"); //会接收到订阅之前的事件 需要hook
    }

    public void handler5(View view){
        LiveDataBus.get().with("name",String.class).setValue("发送给已启动组件事件"); //不会马上接受 在可见的生命周期上接受

    }

    public void handler6(View view){
       startActivity(new Intent(Main2Activity.this,Main3Activity.class));
    }


    public void postEvent(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testViewModel.getLiveData().postValue("发送事件");
            }
        },2000);

    }
}
