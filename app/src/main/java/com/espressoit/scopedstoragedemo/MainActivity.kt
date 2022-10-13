package com.espressoit.scopedstoragedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.espressoit.scopedstoragedemo.savefile.presentation.SaveFileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, SaveFileFragment(), "tag")
            .commit()
    }
}
