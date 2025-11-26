package com.feedickssmix.feedmixchick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.feedickssmix.feedmixchick.ui.theme.Gold
import com.feedickssmix.feedmixchick.ui.theme.GoldBrown
import com.feedickssmix.feedmixchick.ui.theme.GreenHealth
import com.feedickssmix.feedmixchick.ui.theme.RedWarning
import com.feedickssmix.feedmixchick.ui.theme.WheatGradient

@Composable
fun IngredientsScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var energy by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }

    val vm: IngredientViewModel = hiltViewModel()
    val ingredients by vm.ingredients.collectAsState()

//    val list = listOf(
//        "Wheat" to "12% protein • Energy grain",
//        "Corn" to "9% protein • High energy",
//        "Soybean Meal" to "44% protein • Top source",
//        "Fish Meal" to "65% protein • Premium",
//        "Limestone" to "38% calcium • Shell strength",
//        "Oyster Shell" to "Natural calcium",
//        "Premix" to "Vitamins & minerals"
//    )
//    val all = (list + (customIngredients.map { it to "" })).toMutableList()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Custom Ingredient") },
            text = { OutlinedTextField(value = newName, onValueChange = { newName = it }, label = { Text("Name") }) },
            confirmButton = {
                Button(onClick = {
                    if (newName.isNotBlank()) {
                        vm.addCustomIngredient(newName)
                        showDialog = false
                        newName = ""
                    }
                }) { Text("Add") }
            },
            dismissButton = { Button(onClick = { showDialog = false }) { Text("Cancel") } }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(WheatGradient).padding(20.dp)) {
        Text("Ingredients Library", style = MaterialTheme.typography.headlineMedium, color = Gold)
        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            items(ingredients.size) { i ->
                val recipe = ingredients[i]
                Card(Modifier.padding(8.dp).aspectRatio(1f), elevation = CardDefaults.cardElevation(12.dp), colors = CardDefaults.cardColors(Color.White)) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Circle, null, modifier = Modifier.size(48.dp), tint = Green)
                        Spacer(Modifier.height(8.dp))
                        Text(recipe.name, fontWeight = FontWeight.Bold)
                        Text("${recipe.energy} Energy & ${recipe.protein} protein", fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Gold)) {
            Text("Add Custom Ingredient")
        }
    }
}