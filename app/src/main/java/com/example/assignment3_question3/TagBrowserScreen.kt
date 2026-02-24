package com.example.assignment3_question3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagBrowserScreen() {
    val tags = remember {
        listOf(
            "Material3", "Networking", "Databases", "Testing", "Security",
            "Maps", "Media", "Performance", "Accessibility", "Animations"
        )
    }

    var selectedTags by remember { mutableStateOf(setOf<String>()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun toggle(tag: String) {
        selectedTags = if (selectedTags.contains(tag)) selectedTags - tag else selectedTags + tag
    }

    Scaffold(
        // Esto evita problemas de recorte por insets del sistema
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tag Browser") },
                actions = {
                    IconButton(
                        onClick = {
                            selectedTags = emptySet()
                            scope.launch { snackbarHostState.showSnackbar("Selection cleared") }
                        }
                    ) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear selection")
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Selected Tags area (actualiza con taps)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // más padding para evitar que “toque” el borde redondeado
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Selected Tags", style = MaterialTheme.typography.titleMedium)

                        if (selectedTags.isEmpty()) {
                            Text(
                                "None selected",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            // FlowColumn (segundo uso distinto a FlowRow)
                            FlowColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 120.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                selectedTags.toList().sorted().forEach { tag ->
                                    FilterChip(
                                        selected = true,
                                        onClick = { toggle(tag) },
                                        label = { Text(tag) },
                                        leadingIcon = {
                                            Icon(Icons.Filled.Check, contentDescription = null)
                                        },
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = true
                                        )
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    selectedTags = emptySet()
                                    scope.launch { snackbarHostState.showSnackbar("Selection cleared") }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                Text("Clear Selected Tags")
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.fillMaxWidth())

                Text("Browse Tags", style = MaterialTheme.typography.titleMedium)

                // FlowRow (chips que envuelven)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    tags.forEach { tag ->
                        val isSelected = selectedTags.contains(tag)

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                toggle(tag)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (isSelected) "Removed: $tag" else "Selected: $tag"
                                    )
                                }
                            },
                            label = { Text(tag) },
                            leadingIcon = if (isSelected) {
                                { Icon(Icons.Filled.Check, contentDescription = null) }
                            } else null,
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected
                            )
                        )
                    }
                }
            }
        }
    }
}