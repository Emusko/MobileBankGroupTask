package az.mobile.bankgroup.task.presentation.screens.symbolDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SymbolDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: SymbolDetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(text = state.symbol, style = MaterialTheme.typography.headlineMedium)
        if (state.found) {
            Text(text = state.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "$" + String.format("%.2f", state.price), style = MaterialTheme.typography.titleMedium)
            Text(text = state.description, style = MaterialTheme.typography.bodyLarge)
        } else {
            Text(text = "Symbol not found", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
