package com.kiroglue.backgroundtasks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kiroglue.backgroundtasks.threads.Thread1
import com.kiroglue.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {

    private var uiCounter = 0
    private lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initCase1()
        initCase2()
        initCase3()
        initCase4()

    }





    private fun init(){
        handler = Handler(applicationContext.mainLooper)
    }

    private fun initCase1(){
        buttonUIThread.setOnClickListener {
            Thread.sleep(5000)
        }
        tvUIResponse.text = "Thread no= ${Thread.currentThread().id} UI Counter = $uiCounter"
        btnUIControl.setOnClickListener {
            uiCounter +=1
            tvUIResponse.text = "Thread no= ${Thread.currentThread().id} UI Counter = $uiCounter"
        }
    }

    private fun initCase2() {
        /*
            button1
            cause to crash--> Only the original thread that created a view hierarchy can touch its views.
        */
        button1.setOnClickListener {
            Thread(Runnable {
                for (i in 1..10) {
                    Thread.sleep(1000)
                    textView.text = "Thread no= ${Thread.currentThread().id} Value=$i"
                }
            }).start()
        }
    }

    private fun initCase3() {
        button2.setOnClickListener {
            Thread(Runnable {
                for (i in 1..10) {
                    Thread.sleep(1000)
                    handler.post{
                        textView.text = "Thread no= ${Thread.currentThread().id} Value=$i"
                    }
                }
            }).start()
        }
    }

    private fun initCase4() {
        button3.setOnClickListener {
            Log.i(LOG_TAG, "Button3")

            var otherThread = Thread1()
            otherThread.start()

            Thread(Runnable {
                for (i in 1..10) {
                    Thread.sleep(1000)
                    Log.i(LOG_TAG, "Thread no= ${Thread.currentThread().id} Value=$i")
                    otherThread.handler.sendEmptyMessage(i)
                }
            }).start()
        }
    }



}
