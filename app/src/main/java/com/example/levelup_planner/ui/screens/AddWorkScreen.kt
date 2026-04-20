package com.example.levelup_planner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


enum class WorkType(val xpReward: Int) {
    CLASSWORK(10),
    HOMEWORK(20),
    QUIZ(50),
    TEST(100),
    EXAM(250),
    PROJECT(300);

    fun toDisplayName(): String {
        return name.lowercase().replaceFirstChar { it.uppercase()}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkScreen(
    workName: String,
    onWorkNameChange: (String) -> Unit,
    selectedType: WorkType,
    onTypeChange: (WorkType) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Add Work",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = workName,
            onValueChange = onWorkNameChange,
            label = { Text("Work Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedType.toDisplayName(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Assignment Type)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}
            ) {
                WorkType.entries.forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = type.toDisplayName())
                                Text(
                                    text = "+${type.xpReward} XP",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        onClick = {
                            onTypeChange(type)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            enabled = workName.trim().isNotEmpty(),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Work")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancel,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}