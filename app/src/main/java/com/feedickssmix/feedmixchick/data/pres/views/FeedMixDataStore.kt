package com.feedickssmix.feedmixchick.data.pres.views

import android.annotation.SuppressLint
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel

class FeedMixDataStore : ViewModel(){
    val feedMixViList: MutableList<FeedMixVi> = mutableListOf()
    var feedMixIsFirstCreate = true
    @SuppressLint("StaticFieldLeak")
    lateinit var feedMixContainerView: FrameLayout
    @SuppressLint("StaticFieldLeak")
    lateinit var feedMixView: FeedMixVi

}