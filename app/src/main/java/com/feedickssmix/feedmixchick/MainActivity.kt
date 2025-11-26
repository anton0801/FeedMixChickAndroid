package com.feedickssmix.feedmixchick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.feedickssmix.feedmixchick.CalculatorScreen
import com.feedickssmix.feedmixchick.HomeScreen
import com.feedickssmix.feedmixchick.IngredientsScreen
import com.feedickssmix.feedmixchick.ReportsScreen
import com.feedickssmix.feedmixchick.SettingsScreen
import com.feedickssmix.feedmixchick.ui.theme.FeedMixChickTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedMixChickTheme {
                val navController = rememberNavController()
                val savedRecipes = remember { mutableStateListOf<Recipe>() }
                val customIngredients = remember { mutableStateListOf<String>() }
                Scaffold(
                    bottomBar = { BottomNav(navController) },
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->
                    NavHost(navController, startDestination = "home", Modifier.padding(padding)) {
                        composable("home") { HomeScreen(savedRecipes, navController) }
                        composable("mix") { CalculatorScreen() }
                        composable("ingredients") { IngredientsScreen() }
                        composable("reports") { ReportsScreen() }
                        composable("settings") { SettingsScreen() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: androidx.navigation.NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
        tonalElevation = 16.dp
    ) {
        val items = listOf(
            "Home" to Icons.Default.Home,
            "Mix" to Icons.Default.Calculate,
            "Ingredients" to Icons.Default.Grass,
            "Reports" to Icons.Default.BarChart,
            "Settings" to Icons.Default.Settings
        )
        items.forEach { (title, icon) ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(title.lowercase()) { launchSingleTop = true } },
                icon = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
                label = { Text(title, style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}