package com.feedickssmix.feedmixchick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feedickssmix.feedmixchick.ui.theme.Gold
import com.feedickssmix.feedmixchick.ui.theme.GoldBrown
import com.feedickssmix.feedmixchick.ui.theme.Green
import com.feedickssmix.feedmixchick.ui.theme.GreenHealth
import com.feedickssmix.feedmixchick.ui.theme.WheatGradient

@Composable
fun HomeScreen(recipes: List<Recipe>, navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WheatGradient)
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        Icon(Icons.Default.Egg, null, modifier = Modifier.size(100.dp), tint = GoldBrown)
        Text("FeedMix Pro", style = MaterialTheme.typography.headlineLarge, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = GoldBrown)
        Text("Perfect feed for your flock", style = MaterialTheme.typography.titleMedium, color = Color.Black.copy(alpha = 0.7f))

        Spacer(Modifier.height(32.dp))
        if (recipes.isNotEmpty()) {
            val last = recipes.last()
            Card(elevation = CardDefaults.cardElevation(16.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Last Recipe", color = Gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(last.name, fontSize = 22.sp)
                    Text("${"%.1f".format(last.protein)}% Protein • ${"%.1f".format(last.calcium)}% Calcium • Perfect", color = Green)
                }
            }
        }

        Spacer(Modifier.height(32.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Last Mix", style = MaterialTheme.typography.titleLarge, color = GoldBrown)
                Spacer(Modifier.height(8.dp))
                Text("Layer Mix #3 • 18.2% Protein • Perfect", color = GreenHealth)
            }
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GoldBrown.copy(alpha = 0.15f))
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Lightbulb, null, tint = GoldBrown)
                Spacer(Modifier.width(12.dp))
                Text("Tip of the Day: Add fish oil in winter for better egg quality!", color = Color.Black)
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("mix")
            },
            modifier = Modifier.height(60.dp).fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = GreenHealth),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Create New Mix", fontSize = 18.sp, color = Color.White)
        }
    }
}