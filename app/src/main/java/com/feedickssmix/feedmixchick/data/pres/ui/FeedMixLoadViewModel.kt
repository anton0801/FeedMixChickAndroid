package com.helathchickapp.chickhealth.sr.pres.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feedickssmix.feedmixchick.FeedMixAppsFlyerState
import com.feedickssmix.feedmixchick.MainApplication
import com.feedickssmix.feedmixchick.data.handlers.FeedMixChickLocalStorageManager
import com.feedickssmix.feedmixchick.data.domain.data.FeedMixChSystemServiceI
import com.feedickssmix.feedmixchick.data.domain.usecases.FeedMixGetAllUseCaseInApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedMixLoadViewModel(
    private val feedMixGetAllUseCaseInApp: FeedMixGetAllUseCaseInApp,
    private val feedMixChickLocalStorageManager: FeedMixChickLocalStorageManager,
    private val feedMixChSystemServiceI: FeedMixChSystemServiceI
) : ViewModel() {

    private val _chickHealthHomeScreenState: MutableStateFlow<FeedMixHomeScreenState> =
        MutableStateFlow(FeedMixHomeScreenState.FeedMixLoading)
    val chickHealthHomeScreenState = _chickHealthHomeScreenState.asStateFlow()

    private var eggLabelGetApps = false

    init {
        viewModelScope.launch {
            when (feedMixChickLocalStorageManager.feedMixAppState) {
                0 -> {
                    if (feedMixChSystemServiceI.feedMixCheckInternetConnection()) {
                        MainApplication.feedMMConversionFlow.collect {
                            when (it) {
                                FeedMixAppsFlyerState.FeedMixDefault -> {}
                                FeedMixAppsFlyerState.FeedMixError -> {
                                    feedMixChickLocalStorageManager.feedMixAppState = 2
                                    _chickHealthHomeScreenState.value =
                                        FeedMixHomeScreenState.FeedMixError
                                    eggLabelGetApps = true
                                }

                                is FeedMixAppsFlyerState.FeedMixSuccess -> {
                                    if (!eggLabelGetApps) {
                                        feedMixGetData(it.feedMixxChickkData)
                                        eggLabelGetApps = true
                                    }
                                }
                            }
                        }
                    } else {
                        _chickHealthHomeScreenState.value =
                            FeedMixHomeScreenState.FeedMixNotInternet
                    }
                }

                1 -> {
                    if (feedMixChSystemServiceI.feedMixCheckInternetConnection()) {
                        if (MainApplication.FEED_MIX_FB_LI != null) {
                            _chickHealthHomeScreenState.value =
                                FeedMixHomeScreenState.FeedMixSuccess(
                                    MainApplication.FEED_MIX_FB_LI.toString()
                                )
                        } else if (System.currentTimeMillis() / 1000 > feedMixChickLocalStorageManager.feedMixExpired) {
                            Log.d(
                                MainApplication.FEED_MIX_MAIN_TAG,
                                "Current time more then expired, repeat request"
                            )
                            MainApplication.feedMMConversionFlow.collect {
                                when (it) {
                                    FeedMixAppsFlyerState.FeedMixDefault -> {}
                                    FeedMixAppsFlyerState.FeedMixError -> {
                                        _chickHealthHomeScreenState.value =
                                            FeedMixHomeScreenState.FeedMixSuccess(
                                                feedMixChickLocalStorageManager.feedMixSavedUrl
                                            )
                                        eggLabelGetApps = true
                                    }

                                    is FeedMixAppsFlyerState.FeedMixSuccess -> {
                                        if (!eggLabelGetApps) {
                                            feedMixGetData(it.feedMixxChickkData)
                                            eggLabelGetApps = true
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d(
                                MainApplication.FEED_MIX_MAIN_TAG,
                                "Current time less then expired, use saved url"
                            )
                            _chickHealthHomeScreenState.value =
                                FeedMixHomeScreenState.FeedMixSuccess(
                                    feedMixChickLocalStorageManager.feedMixSavedUrl
                                )
                        }
                    } else {
                        _chickHealthHomeScreenState.value =
                            FeedMixHomeScreenState.FeedMixNotInternet
                    }
                }

                2 -> {
                    _chickHealthHomeScreenState.value =
                        FeedMixHomeScreenState.FeedMixError
                }
            }
        }
    }


    private suspend fun feedMixGetData(conversation: MutableMap<String, Any>?) {
        val eggLabelData = feedMixGetAllUseCaseInApp.invoke(conversation)
        if (feedMixChickLocalStorageManager.feedMixAppState == 0) {
            if (eggLabelData == null) {
                feedMixChickLocalStorageManager.feedMixAppState = 2
                _chickHealthHomeScreenState.value =
                    FeedMixHomeScreenState.FeedMixError
            } else {
                feedMixChickLocalStorageManager.feedMixAppState = 1
                feedMixChickLocalStorageManager.apply {
                    feedMixExpired = eggLabelData.feedMixExpires
                    feedMixSavedUrl = eggLabelData.feedMixUrl
                }
                _chickHealthHomeScreenState.value =
                    FeedMixHomeScreenState.FeedMixSuccess(eggLabelData.feedMixUrl)
            }
        } else {
            if (eggLabelData == null) {
                _chickHealthHomeScreenState.value =
                    FeedMixHomeScreenState.FeedMixSuccess(feedMixChickLocalStorageManager.feedMixSavedUrl)
            } else {
                feedMixChickLocalStorageManager.apply {
                    feedMixExpired = eggLabelData.feedMixExpires
                    feedMixSavedUrl = eggLabelData.feedMixUrl
                }
                _chickHealthHomeScreenState.value =
                    FeedMixHomeScreenState.FeedMixSuccess(eggLabelData.feedMixUrl)
            }
        }
    }


    sealed class FeedMixHomeScreenState {
        data object FeedMixLoading : FeedMixHomeScreenState()
        data object FeedMixError : FeedMixHomeScreenState()
        data class FeedMixSuccess(val data: String) : FeedMixHomeScreenState()
        data object FeedMixNotInternet : FeedMixHomeScreenState()
    }
}