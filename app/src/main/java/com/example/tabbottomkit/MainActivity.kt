package com.example.tabbottomkit

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import com.example.tabbottomkit.demo1.Demo1Activity
import com.example.tabbottomkit.demo2.Demo2Activity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_tab_bottom1 -> {
                startActivity(Intent(this, Demo1Activity::class.java))
            }
            R.id.tv_tab_bottom2 -> {
                startActivity(Intent(this, Demo2Activity::class.java))
            }

        }
    }
}