package az.mobile.bankgroup.task.uikit.stock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun StockPriceCard(
    symbol: String,
    currentPrice: Double,
    indicatorUp: Boolean,
    shouldFlash: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val indicatorColor = if (indicatorUp) Color(0xFF1B8F3A) else Color(0xFFC53929)
    val indicatorIcon = if (indicatorUp) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }
    val basePriceColor = MaterialTheme.colorScheme.onSurface
    var flashColor by remember { mutableStateOf(basePriceColor) }
    val animatedPriceColor by animateColorAsState(targetValue = flashColor, label = "priceFlash")
    LaunchedEffect(currentPrice, shouldFlash, indicatorUp) {
        if (shouldFlash) {
            flashColor = if (indicatorUp) Color(0xFF1B8F3A) else Color(0xFFC53929)
            delay(1_000L)
            flashColor = basePriceColor
        } else {
            flashColor = basePriceColor
        }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "$" + String.format("%.2f", currentPrice),
                    style = MaterialTheme.typography.bodyLarge,
                    color = animatedPriceColor,
                )
            }
            Icon(
                imageVector = indicatorIcon,
                contentDescription = if (indicatorUp) "price up" else "price down",
                tint = indicatorColor,
            )
        }
    }
}
