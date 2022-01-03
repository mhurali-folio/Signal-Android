package org.thoughtcrime.securesms.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.thoughtcrime.securesms.R
import org.thoughtcrime.securesms.keyvalue.SignalStore

class OnBoardingMainMenuActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_onboarding_main_menu)
    val create = findViewById<Button>(R.id.createAccount)
    create.setOnClickListener {
      markAndFinish()
    }

    val haveAccount = findViewById<Button>(R.id.haveAnAccount)
    haveAccount.setOnClickListener {
      markAndFinish()
    }

    setImmersiveMode()
  }

  private fun markAndFinish() {
    SignalStore.onboarding().setShowOnBoardingFlow(false)
    val pasPhraseIntent = intent.getParcelableExtra<Intent>("next_intent")?.getParcelableExtra<Intent>("next_intent")
    startActivity(pasPhraseIntent)
    finish()
  }

  fun setImmersiveMode() {
    window.decorView.systemUiVisibility = (
      View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_FULLSCREEN
      )
  }


}