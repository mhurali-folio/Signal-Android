package org.thoughtcrime.securesms.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntroBase
import org.thoughtcrime.securesms.R

class OnBoardingActivity : AppIntroBase() {
  override val layoutId: Int
    get() = R.layout.activity_onboarding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Make sure you don't call setContentView!

    // Call addSlide passing your Fragments.
    // You can use AppIntroFragment to use a pre-built fragment
    addSlide(OnBoardingFragment.newInstance(
      title = "Invite",
      description = "Bring you and your Peeps into a safe & secure world away from big brothers",
      titleColor = ContextCompat.getColor(applicationContext, R.color.black),
      descriptionColor = ContextCompat.getColor(applicationContext, R.color.core_grey_35),
      imageDrawable = R.drawable.bg_onboarding_01
    ))
    addSlide(OnBoardingFragment.newInstance(
      title = "Connect",
      description = "It's your private circle or your own trusted peeps with those that matter most to you",
      titleColor = ContextCompat.getColor(applicationContext, R.color.black),
      descriptionColor = ContextCompat.getColor(applicationContext, R.color.core_grey_35),
      imageDrawable = R.drawable.bg_onboarding_03
    ))
    addSlide(OnBoardingFragment.newInstance(
      title = "Share",
      description = "Secure exchanges with end-to-end encryption to keep out prying eyes",
      titleColor = ContextCompat.getColor(applicationContext, R.color.black),
      descriptionColor = ContextCompat.getColor(applicationContext, R.color.core_grey_35),
      imageDrawable = R.drawable.bg_onboarding_02
    ))

    setIndicatorColor(
      selectedIndicatorColor = ContextCompat.getColor(applicationContext, R.color.core_ultramarine),
      unselectedIndicatorColor = ContextCompat.getColor(applicationContext, R.color.core_grey_05)
    )
  }

  override fun onDonePressed(currentFragment: Fragment?) {
    super.onDonePressed(currentFragment)
    // Decide what to do when the user clicks on "Done"
    finish()

    val onboardingMainMenu = Intent(this, OnBoardingMainMenuActivity::class.java)
    onboardingMainMenu.putExtra("next_intent", intent)
    startActivity(onboardingMainMenu)
  }
}