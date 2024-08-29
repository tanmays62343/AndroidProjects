package com.trx.activity

import android.app.Application
import io.intercom.android.sdk.Intercom

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Intercom.initialize(this, "android_sdk-a84246272a6e2b97896a0a28e4d0367d919d22ed", "yfjfqqu8")
        Intercom.client().loginUnidentifiedUser() // If you have identifying information for your user you can use loginIdentifiedUser() instead
        Intercom.client().setLauncherVisibility(Intercom.Visibility.VISIBLE)

    }

}