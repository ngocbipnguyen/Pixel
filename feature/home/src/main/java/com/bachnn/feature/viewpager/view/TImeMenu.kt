package com.bachnn.feature.viewpager.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bachnn.data.model.LeaderCategory
import com.bachnn.data.model.clear


@Composable
fun TimeMenu(categoryList: List<LeaderCategory>) {
    var expanded by remember { mutableStateOf(false) }

    var categorySelected by remember { mutableStateOf("All time") }

    Box(
        modifier = Modifier
    ) {
        AssistChip(
            onClick = {
                expanded = !expanded
            },
            label = { Text(categorySelected, style = MaterialTheme.typography.titleMedium) },
            trailingIcon = {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categoryList.forEach { option ->
                DropdownMenuItem(
                    text = {
                        AssistChip(
                            onClick = {
                                categorySelected = option.title
                                categoryList.map { it -> it.clear() }
                                option.isSelect = true
                            },
                            label = { Text(option.title, style = MaterialTheme.typography.titleSmall) },
                            trailingIcon = {
                                if (option.isSelect) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Done icon",
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                } else {

                                }
                            })
                    },
                    onClick = { /* Do something... */ }
                )
            }
        }
    }
}