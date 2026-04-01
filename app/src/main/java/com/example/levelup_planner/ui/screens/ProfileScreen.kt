package com.example.levelup_planner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup_planner.R
import com.example.levelup_planner.model.AvatarChoice
import com.example.levelup_planner.model.ThemeMode

@Composable
fun ProfileScreen(
    username: String,
    selectedTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    selectedAvatar: AvatarChoice,
    onAvatarSelected: (AvatarChoice) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = username,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Current avatar display
        Image(
            painter = painterResource(
                id = if (selectedAvatar == AvatarChoice.LIGHT) R.drawable.avatar_light
                else R.drawable.avatar_dark
            ),
            contentDescription = "Current avatar",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Avatar selection
        Text(
            text = "Choose Avatar",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarOption(
                drawableRes = R.drawable.avatar_light,
                label = "Light",
                selected = selectedAvatar == AvatarChoice.LIGHT,
                onClick = { onAvatarSelected(AvatarChoice.LIGHT) }
            )
            AvatarOption(
                drawableRes = R.drawable.avatar_dark,
                label = "Dark",
                selected = selectedAvatar == AvatarChoice.DARK,
                onClick = { onAvatarSelected(AvatarChoice.DARK) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Theme selection
        Text(
            text = "Choose Theme",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        ThemeOption(
            text = "Light",
            selected = selectedTheme == ThemeMode.LIGHT,
            onClick = { onThemeSelected(ThemeMode.LIGHT) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ThemeOption(
            text = "Dark",
            selected = selectedTheme == ThemeMode.DARK,
            onClick = { onThemeSelected(ThemeMode.DARK) }
        )
    }
}

@Composable
private fun AvatarOption(
    drawableRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = label,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .then(
                    if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    else Modifier.border(1.dp, Color.Gray, CircleShape)
                )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ThemeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text)
        }
    }
}
