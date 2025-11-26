package com.feedickssmix.feedmixchick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun ReportsScreen() {
    val vm: RecipeViewModel = hiltViewModel()
    val recipes by vm.recipes.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(WheatGradient).padding(24.dp)) {
        Text("Reports & Analytics", style = MaterialTheme.typography.headlineMedium, color = Gold)

        Card(Modifier.fillMaxWidth().height(200.dp), colors = CardDefaults.cardColors(Color.White)) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cost per kg: $0.32 ↓ 8%", fontSize = 24.sp, color = Green)
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(Modifier.fillMaxSize().background(WheatGradient).padding(20.dp)) {
            Text("Saved Recipes: ${recipes.size}", fontSize = 24.sp)
            LazyColumn {
                items(recipes) { recipe ->
                    Card(Modifier.padding(8.dp)) {
                        Text("${recipe.name} • ${recipe.protein}% protein")
                    }
                }
            }
        }

    }
}