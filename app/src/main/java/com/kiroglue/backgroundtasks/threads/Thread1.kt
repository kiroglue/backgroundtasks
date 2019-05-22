package com.kiroglue.backgroundtasks.threads

import android.os.Handler
import android.os.Message
import android.util.Log

class Thread1 : Thread(){

    var  handler : Handler1 =
        Handler1()

    override fun run() {
        Log.i("DEMO", "Thread1 is running")
    }

    class Handler1 : Handler() {

        override fun handleMessage(msg: Message?) {
            Log.i("DEMO", "Thread no= ${Thread.currentThread().id} Thread1 is get the message ${msg?.what}")
        }
    }
}