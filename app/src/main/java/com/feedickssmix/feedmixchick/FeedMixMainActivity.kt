package com.feedickssmix.feedmixchick

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.feedickssmix.feedmixchick.data.handlers.FeedMixNotificationsPushHandler
import com.feedickssmix.feedmixchick.data.layout.FeedMixGlobalLayoutUtils
import com.feedickssmix.feedmixchick.data.layout.chickHealthSetupSystemBars
import com.feedickssmix.feedmixchick.databinding.ActivityFeedMixMainBinding
import org.koin.android.ext.android.inject

class FeedMixMainActivity : AppCompatActivity() {

    private val feedMixPushHandler by inject<FeedMixNotificationsPushHandler>()

    private lateinit var binding: ActivityFeedMixMainBinding


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            chickHealthSetupSystemBars()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chickHealthSetupSystemBars()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityFeedMixMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val feedMixRootView = findViewById<View>(android.R.id.content)
        FeedMixGlobalLayoutUtils().feedMixAssistActivity(this)
        ViewCompat.setOnApplyWindowInsetsListener(feedMixRootView) { feedMixView, feedMixInsets ->
            val feedMixSystemBars = feedMixInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val feedMixDisplayCutout =
                feedMixInsets.getInsets(WindowInsetsCompat.Type.displayCutout())
            val feedMixIme = feedMixInsets.getInsets(WindowInsetsCompat.Type.ime())
            val feedMixTopPadding = maxOf(feedMixSystemBars.top, feedMixDisplayCutout.top)
            val feedMixLeftPadding = maxOf(feedMixSystemBars.left, feedMixDisplayCutout.left)
            val feedMixRightPadding = maxOf(feedMixSystemBars.right, feedMixDisplayCutout.right)
            window.setSoftInputMode(MainApplication.feedMInputMode)

            if (window.attributes.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) {
                val feedMixBottomInset =
                    maxOf(feedMixSystemBars.bottom, feedMixDisplayCutout.bottom)
                feedMixView.setPadding(
                    feedMixLeftPadding,
                    feedMixTopPadding,
                    feedMixRightPadding,
                    0
                )
                feedMixView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = feedMixBottomInset
                }
            } else {
                val feedMixBottomInset =
                    maxOf(feedMixSystemBars.bottom, feedMixDisplayCutout.bottom, feedMixIme.bottom)
                feedMixView.setPadding(
                    feedMixLeftPadding,
                    feedMixTopPadding,
                    feedMixRightPadding,
                    0
                )
                feedMixView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = feedMixBottomInset
                }
            }
            WindowInsetsCompat.CONSUMED
        }
        feedMixPushHandler.chickHealthHandlePush(intent.extras)
    }

    override fun onResume() {
        super.onResume()
        chickHealthSetupSystemBars()
    }

}