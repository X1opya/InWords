package com.x1opya.inwords.OthersClass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
