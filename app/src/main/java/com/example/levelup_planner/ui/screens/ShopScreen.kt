package com.example.levelup_planner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup_planner.model.AvatarChoice
import com.example.levelup_planner.model.ThemeMode
import com.example.levelup_planner.ui.theme.themeSwatchColor

@Composable
fun ShopScreen(
    points: Int,
    ownedAvatars: Set<AvatarChoice>,
    ownedThemes: Set<ThemeMode>,
    onPurchaseAvatar: (AvatarChoice) -> Unit,
    onPurchaseTheme: (ThemeMode) -> Unit
) {
    val purchasableAvatars = AvatarChoice.values().filter { !it.isDefault }
    val purchasableThemes = ThemeMode.values().filter { !it.isDefault }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Shop",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Points: $points",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Avatars",
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(purchasableAvatars) { avatar ->
            ShopAvatarCard(
                avatar = avatar,
                owned = avatar in ownedAvatars,
                canAfford = points >= avatar.price,
                onPurchase = { onPurchaseAvatar(avatar) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Themes",
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(purchasableThemes) { theme ->
            ShopThemeCard(
                theme = theme,
                owned = theme in ownedThemes,
                canAfford = points >= theme.price,
                onPurchase = { onPurchaseTheme(theme) }
            )
        }
    }
}

@Composable
private fun ShopAvatarCard(
    avatar: AvatarChoice,
    owned: Boolean,
    canAfford: Boolean,
    onPurchase: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = avatar.drawableRes),
                    contentDescription = avatar.displayName,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = avatar.displayName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${avatar.price} pts",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            PurchaseButton(
                owned = owned,
                canAfford = canAfford,
                onPurchase = onPurchase
            )
        }
    }
}

@Composable
private fun ShopThemeCard(
    theme: ThemeMode,
    owned: Boolean,
    canAfford: Boolean,
    onPurchase: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = themeSwatchColor(theme),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = theme.displayName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${theme.price} pts",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            PurchaseButton(
                owned = owned,
                canAfford = canAfford,
                onPurchase = onPurchase
            )
        }
    }
}

@Composable
private fun PurchaseButton(
    owned: Boolean,
    canAfford: Boolean,
    onPurchase: () -> Unit
) {
    Button(
        onClick = onPurchase,
        enabled = !owned && canAfford,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = when {
                owned -> "Owned"
                !canAfford -> "Locked"
                else -> "Buy"
            }
        )
    }
}

