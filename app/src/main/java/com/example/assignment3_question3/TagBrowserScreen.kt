package com.example.assignment3_question3

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagBrowserScreen() {
    // Dynamic tags list (could be loaded; kept local for assignment)
    val allTags = listOf(
        "Kotlin", "Compose", "UI", "Android", "Material3",
        "Networking", "Databases", "Testing", "Security", "Maps",
        "Media", "Performance", "Accessibility", "Animations"
    )

    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    // Simple filter state (FlowColumn purpose)
    var onlySelected by remember { mutableStateOf(false) }
    var showShortTags by remember { mutableStateOf(false) }

    val visibleTags = allTags
        .asSequence()
        .filter { if (onlySelected) it in selectedTags else true }
        .filter { if (showShortTags) it.length <= 6 else true }
        .toList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tag Browser") }
            )
        }
    ) { innerPadding ->
        val screenPadding = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxWidth()

        // Selected area (must update)
        Card(
            modifier = screenPadding,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = "Selected Tags",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedTags.isEmpty()) {
                    AssistChip(
                        onClick = {},
                        label = { Text("None selected") }
                    )
                } else {
                    selectedTags.forEach { tag ->
                        AssistChip(
                            onClick = { selectedTags = selectedTags - tag },
                            label = { Text(tag) }
                        )
                    }
                }
            }

            Divider(modifier = Modifier.fillMaxWidth())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { selectedTags = emptySet() }) {
                    Text("Clear")
                }
                Text(
                    text = "Tap selected chip above to remove",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Filters section using FlowColumn (second required flow layout)
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = "Filters (FlowColumn)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )

            FlowColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachColumn = 2 // helps demonstrate multi-column wrap when space allows
            ) {
                FilterToggleRow(
                    label = "Only selected",
                    checked = onlySelected,
                    onCheckedChange = { onlySelected = it }
                )
                FilterToggleRow(
                    label = "Short tags (≤ 6)",
                    checked = showShortTags,
                    onCheckedChange = { showShortTags = it }
                )
            }
        }

        // Tag chips section (FlowRow requirement)
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = "All Tags",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                visibleTags.forEach { tag ->
                    val isSelected = tag in selectedTags

                    // Visual selected state change:
                    // - FilterChip selected colors by M3
                    // - Optional: different elevation via surrounding Card is not needed; chip state suffices
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedTags = if (isSelected) selectedTags - tag else selectedTags + tag
                        },
                        label = { Text(tag) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}