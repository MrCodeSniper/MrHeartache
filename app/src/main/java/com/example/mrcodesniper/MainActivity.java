package com.example.mrcodesniper;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    public void route(View view){
        startActivity(new Intent(this,Main2Activity.class));
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String rootDir = MMKV.initialize(this);
        Log.d("tag","mmkv root: " + rootDir);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        long startTime=System.currentTimeMillis();
//        sp();
        mmkv();
        long endTime=System.currentTimeMillis();
        Log.d("tag",endTime-startTime+"MS");


        LiveDataBus.get()
                .with("name",String.class)
                .observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("xxx","bus:"+s);
            }
        });
    }


    private void sp(){
        SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();
        Random random=new Random();
        for(int i=0;i<100000;i++){
            editor.putInt("INT_KEY",random.nextInt(Integer.MAX_VALUE));
        }
        editor.commit();
    }

    private void mmkv(){
        Random random=new Random();
        MMKV kv = MMKV.defaultMMKV();
        kv.encode("int",10);
    }

}
