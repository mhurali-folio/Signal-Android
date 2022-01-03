package org.thoughtcrime.securesms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }
}