package com.feedickssmix.feedmixchick.di

import android.content.Context
import androidx.room.Room
import com.feedickssmix.feedmixchick.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "feedmix_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideIngredientDao(db: AppDatabase) = db.ingredientDao()

    @Provides
    @Singleton
    fun provideRecipeDao(db: AppDatabase) = db.recipeDao()
}