package com.feedickssmix.feedmixchick

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val protein: Double,
    val energy: Double,
    val calcium: Double,
    val isCustom: Boolean = false
)

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birdType: String,
    val protein: Double,
    val calcium: Double,
    val energy: Double,
    val timestamp: Long = System.currentTimeMillis()
)

data class MixtureItem(val ingredient: Ingredient, var percent: Float = 0f)

@Database(entities = [Ingredient::class, Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao
}

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Recipe>>

    @Insert
    suspend fun insert(recipe: Recipe)
}

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients ORDER BY name")
    fun getAll(): Flow<List<Ingredient>>

    @Insert
    suspend fun insert(ingredient: Ingredient)
}