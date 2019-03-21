package com.example.mrcodesniper;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    private MutableLiveData<String> liveData;

    public MutableLiveData<String> getLiveData() {
        if(liveData==null){
            liveData=new MutableLiveData<>();
        }
        return liveData;
    }
}
