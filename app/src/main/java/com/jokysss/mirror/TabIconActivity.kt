package com.jokysss.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_tab_icon.*

class TabIconActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_icon)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
        })
    }
}
