package com.feedickssmix.feedmixchick.data.domain.usecases

import android.util.Log
import com.feedickssmix.feedmixchick.MainApplication
import com.feedickssmix.feedmixchick.data.domain.data.FeedMixPushTokenUseCase
import com.feedickssmix.feedmixchick.data.domain.data.FeedMixRepositoryImpl
import com.feedickssmix.feedmixchick.data.domain.data.FeedMixChSystemServiceI
import com.feedickssmix.feedmixchick.data.domain.model.FeedMIxEntity
import com.feedickssmix.feedmixchick.data.domain.model.FeedMixParam

class FeedMixGetAllUseCaseInApp(
    private val feedMixRepositoryImpl: FeedMixRepositoryImpl,
    private val feedMixChSystemServiceI: FeedMixChSystemServiceI,
    private val feedMixPushTokenUseCase: FeedMixPushTokenUseCase,
) {
    suspend operator fun invoke(conversion: MutableMap<String, Any>?): FeedMIxEntity? {
        val params = FeedMixParam(
            feedMixLocale = feedMixChSystemServiceI.getLocaleOfUserFeedMix(),
            feedMixPushToken = feedMixPushTokenUseCase.eggLabelGetToken(),
            feedMixAfId = feedMixChSystemServiceI.getAppsflyerIdForApp()
        )
        Log.d(MainApplication.FEED_MIX_MAIN_TAG, "Params for request: $params")
        return feedMixRepositoryImpl.feedMixAppGetClient(params, conversion)
    }


}