package com.example.levelup_planner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.levelup_planner.model.ClassItem
import com.example.levelup_planner.model.WorkItem

@Composable
fun ClassScreen(
    classItem: ClassItem,
    workList: List<WorkItem>,
    onBack: () -> Unit,
    onCompleteWork: (WorkItem) -> Unit,
    onAddWorkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("Back") }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = classItem.name, style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Level ${classItem.level}", style = MaterialTheme.typography.titleLarge)
            Text("${classItem.xp}/100 XP", style = MaterialTheme.typography.titleMedium)
        }

        LinearProgressIndicator(
            progress = { classItem.xp / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(vertical = 8.dp)
                .clip(CircleShape)
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(text = "Assignments", style = MaterialTheme.typography.titleLarge)

        Button(
            onClick = onAddWorkClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Work")
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(workList) { task ->
                WorkCard(task = task, onComplete = { onCompleteWork(task) })
            }
        }
    }
}

@Composable
fun WorkCard(task: WorkItem, onComplete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        enabled = !task.done,
        onClick = onComplete
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Due: ${task.due}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "+${task.xp} XP",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}