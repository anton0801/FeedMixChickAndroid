package com.feedickssmix.feedmixchick.data.domain.data

import android.util.Log
import com.feedickssmix.feedmixchick.MainApplication.Companion.FEED_MIX_MAIN_TAG
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.feedickssmix.feedmixchick.data.domain.model.FeedMIxEntity
import com.feedickssmix.feedmixchick.data.domain.model.FeedMixParam
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.lang.Exception

interface FeedMixLabelApi {
    @Headers("Content-Type: application/json")
    @POST("config.php")
    fun feedMixGetClient(
        @Body jsonString: JsonObject,
    ): Call<FeedMIxEntity>
}


private const val CHICK_HEALTH_MAIN = "https://chickheallth.com/"

class FeedMixRepositoryImpl {

    suspend fun chickHealthLabelGetClient(
        feedMixParam: FeedMixParam,
        eggLabelConversion: MutableMap<String, Any>?
    ): FeedMIxEntity? {
        val gson = Gson()
        val api = chickHealthLabelGetApi(CHICK_HEALTH_MAIN, null)

        val eggLabelJsonObject = gson.toJsonTree(feedMixParam).asJsonObject
        eggLabelConversion?.forEach { (key, value) ->
            val element: JsonElement = gson.toJsonTree(value)
            eggLabelJsonObject.add(key, element)
        }
        return try {
            val eggLabelRequest: Call<FeedMIxEntity> = api.feedMixGetClient(
                jsonString = eggLabelJsonObject,
            )
            val eggLabelResult = eggLabelRequest.awaitResponse()
            if (eggLabelResult.code() == 200) {
                eggLabelResult.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d(FEED_MIX_MAIN_TAG, "Retrofit: ${e.message}")
            null
        }
    }


    private fun chickHealthLabelGetApi(url: String, client: OkHttpClient?): FeedMixLabelApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client ?: OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }


}
