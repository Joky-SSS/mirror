package com.jokyxray.ndksimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        System.loadLibrary("helloworld")
        System.loadLibrary("accessmethod")
        var arr = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        var sum = HelloWorld().sumArr(arr)
        txt.text = HelloWorld().sayHello("Joky $sum")
        AccessMethod.callInstanceMethod()
        AccessMethod.callStaticMethod()
    }
}
