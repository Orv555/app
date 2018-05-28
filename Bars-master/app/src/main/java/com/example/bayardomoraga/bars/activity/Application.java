package com.example.bayardomoraga.bars.activity;

import com.tumblr.remember.Remember;

import io.realm.Realm;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Remember.init(getApplicationContext(),"com.example.bayardomoraga.bars.activity");
    }
}
