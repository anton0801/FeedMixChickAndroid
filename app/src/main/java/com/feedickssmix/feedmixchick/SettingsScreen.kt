package com.feedickssmix.feedmixchick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feedickssmix.feedmixchick.ui.theme.Gold
import com.feedickssmix.feedmixchick.ui.theme.WheatGradient

@Composable
fun SettingsScreen() {
    var units by remember { mutableStateOf("Percent") }
    var currency by remember { mutableStateOf("USD") }

    Column(modifier = Modifier.fillMaxSize().background(WheatGradient).padding(24.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium, color = Gold)

        Card {
            ListItem(headlineContent = { Text("Units") }, supportingContent = { Text(units) })
            ListItem(headlineContent = { Text("Currency") }, supportingContent = { Text(currency) })
            ListItem(headlineContent = { Text("Notifications") }, trailingContent = { Switch(checked = true, onCheckedChange = {}) })
            ListItem(headlineContent = { Text("Export All Recipes") }, trailingContent = { Icon(Icons.Default.Download, null) })
        }
    }
}