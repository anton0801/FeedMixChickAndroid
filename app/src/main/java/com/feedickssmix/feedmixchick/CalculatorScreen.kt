package com.feedickssmix.feedmixchick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feedickssmix.feedmixchick.ui.theme.Gold
import com.feedickssmix.feedmixchick.ui.theme.Green
import com.feedickssmix.feedmixchick.ui.theme.Red
import com.feedickssmix.feedmixchick.ui.theme.WheatGradient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val vm: CalculatorViewModel = hiltViewModel()
    val ingredients by vm.ingredients.collectAsState()
    val mixture by vm.mixture.collectAsState()
    val totalPercent by vm.totalPercent.collectAsState()
    val nutrients = vm.calculateNutrients()

    var recipeName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WheatGradient)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Заголовок
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Calculate, null, tint = Gold, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Text(
                "Feed Calculator",
                style = MaterialTheme.typography.headlineLarge,
                color = Gold,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            "Create perfect mix for your birds",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(0.8f)
        )

        Spacer(Modifier.height(20.dp))

        // Поле для названия рецепта
        OutlinedTextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            label = { Text("Recipe Name (e.g. Layer Gold 18%)") },
            leadingIcon = { Icon(Icons.Default.Edit, null) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green)
        )

        Spacer(Modifier.height(20.dp))

        // Список ингредиентов
        Text("Ingredients", style = MaterialTheme.typography.titleLarge, color = Gold)

        if (mixture.isEmpty()) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Green)
                    Spacer(Modifier.height(16.dp))
                    Text("Loading ingredients...", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(mixture) { index, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Иконка зерна
                                Icon(
                                    Icons.Default.Grass,
                                    null,
                                    tint = Green,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.ingredient.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Protein: ${item.ingredient.protein}% • Calcium: ${item.ingredient.calcium}% • Energy: ${item.ingredient.energy} kcal",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }

                                // Процент
                                Text(
                                    text = "${item.percent.toInt()}%",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = if (item.percent > 0) Green else Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(12.dp))

                            // Слайдер
                            Slider(
                                value = item.percent,
                                onValueChange = { vm.updatePercent(index, it) },
                                valueRange = 0f..100f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Green,
                                    activeTrackColor = Green.copy(0.3f),
                                    inactiveTrackColor = Color.LightGray
                                )
                            )
                        }
                    }
                }
            }
        }

        // Итоговая карточка
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        text = "${totalPercent.toInt()}% / 100%",
                        color = if (totalPercent == 100f) Green else Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Protein", color = Color.Gray)
                        Text(
                            "${"%.1f".format(nutrients.protein)}%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Green
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Calcium", color = Color.Gray)
                        Text(
                            "${"%.2f".format(nutrients.calcium)}%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (nutrients.calcium >= 3.0) Green else Red
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Energy", color = Color.Gray)
                        Text(
                            "${"%.0f".format(nutrients.energy)} kcal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Кнопка сохранения
        Button(
            onClick = {
                scope.launch {
                    vm.saveRecipe(recipeName.ifBlank { "My Mix ${System.currentTimeMillis() % 1000}" })
                    recipeName = ""
                }
            },
            enabled = totalPercent == 100f && recipeName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(32.dp)
        ) {
            Icon(Icons.Default.Save, null, tint = Color.White)
            Spacer(Modifier.width(12.dp))
            Text("Save Recipe", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    private val _mixture = MutableStateFlow<List<MixtureItem>>(emptyList())
    val mixture: StateFlow<List<MixtureItem>> = _mixture.asStateFlow()

    private val _totalPercent = MutableStateFlow(0f)
    val totalPercent: StateFlow<Float> = _totalPercent

    init {
        viewModelScope.launch {
            ingredientDao.getAll().collect { list ->
                _ingredients.value = list
                if (list.isNotEmpty() && _mixture.value.isEmpty()) {
                    // Автозаполнение первыми 8 ингредиентами (или всеми, если меньше)
                    val selected = list.take(8)
                    _mixture.value = selected.map { MixtureItem(it, 0f) }
                    distributePercentEvenly()
                }
            }
        }
    }

    fun updatePercent(index: Int, value: Float) {
        if (index !in _mixture.value.indices) return

        val current = _mixture.value.toMutableList()
        val oldValue = current[index].percent
        val diff = value - oldValue

        current[index] = current[index].copy(percent = value.coerceAtMost(100f))

        // Автоматически уменьшаем другие, чтобы сумма не превышала 100%
        if (diff > 0) {
            val remaining = 100f - value
            val othersSum =
                current.filterIndexed { i, _ -> i != index }.sumOf { it.percent.toDouble() }
                    .toFloat()
            if (othersSum > remaining) {
                val scale = remaining / othersSum.coerceAtLeast(0.01f)
                current.forEachIndexed { i, item ->
                    if (i != index) {
                        current[i] = item.copy(percent = (item.percent * scale).coerceAtLeast(0f))
                    }
                }
            }
        }

        _mixture.value = current
        recalculateTotal()
    }

    private fun distributePercentEvenly() {
        val count = _mixture.value.size
        if (count == 0) return
        val percent = 100f / count
        _mixture.value = _mixture.value.map { it.copy(percent = percent) }
        recalculateTotal()
    }

    private fun recalculateTotal() {
        _totalPercent.value = _mixture.value.sumOf { it.percent.toDouble() }.toFloat()
    }

    fun calculateNutrients(): NutrientsResult {
        val total = _totalPercent.value
        if (total == 0f) return NutrientsResult(0.0, 0.0, 0.0)

        val protein = _mixture.value.sumOf { it.ingredient.protein * it.percent } / total
        val calcium = _mixture.value.sumOf { it.ingredient.calcium * it.percent } / total
        val energy = _mixture.value.sumOf { it.ingredient.energy * it.percent } / total

        return NutrientsResult(protein, calcium, energy)
    }

    fun saveRecipe(name: String, birdType: String = "Chicken") {
        viewModelScope.launch {
            val nutrients = calculateNutrients()
            recipeDao.insert(
                Recipe(
                    name = name,
                    birdType = birdType,
                    protein = nutrients.protein,
                    calcium = nutrients.calcium,
                    energy = nutrients.energy
                )
            )
        }
    }
}

data class NutrientsResult(
    val protein: Double,
    val calcium: Double,
    val energy: Double
)

// IngredientViewModel.kt
@HiltViewModel
class IngredientViewModel @Inject constructor(private val dao: IngredientDao) : ViewModel() {
    val ingredients = dao.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCustomIngredient(name: String) {
        viewModelScope.launch {
            dao.insert(
                Ingredient(
                    name = name,
                    protein = 0.0,
                    energy = 0.0,
                    calcium = 0.0,
                    isCustom = true
                )
            )
        }
    }
}

// RecipeViewModel.kt
@HiltViewModel
class RecipeViewModel @Inject constructor(private val dao: RecipeDao) : ViewModel() {
    val recipes = dao.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}