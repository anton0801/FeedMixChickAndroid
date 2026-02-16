package com.feedickssmix.feedmixchick

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.unit.dp
import com.feedickssmix.feedmixchick.ui.theme.Gold
import com.feedickssmix.feedmixchick.ui.theme.WheatGradient
import androidx.core.net.toUri

@Composable
fun SettingsScreen(context: Context) {
    var units by remember { mutableStateOf("Percent") }
    var currency by remember { mutableStateOf("USD") }

    Column(modifier = Modifier.fillMaxSize().background(WheatGradient).padding(24.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium, color = Gold)

        Card {
            ListItem(headlineContent = { Text("Units") }, supportingContent = { Text(units) })
            ListItem(headlineContent = { Text("Currency") }, supportingContent = { Text(currency) })
            ListItem(headlineContent = { Text("Notifications") }, trailingContent = { Switch(checked = true, onCheckedChange = {}) })
            ListItem(headlineContent = { Button(onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW,
                    "https://feedmixchick.com/privacy-policy.html".toUri()))
            }) { Text("Privacy Policy") } }, trailingContent = {
                Button(onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW,
                        "https://feedmixchick.com/privacy-policy.html".toUri()))
                }) { Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, null, modifier = Modifier.size(48.dp), tint = Green) }
            })
        }
    }
}