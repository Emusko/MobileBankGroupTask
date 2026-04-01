package az.mobile.bankgroup.task

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import az.mobile.bankgroup.task.presentation.screens.feed.FeedScreen

@Composable
fun MobilBankNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MobilBankScreen.FEED.route,
        modifier = modifier,
    ) {
        composable(MobilBankScreen.FEED.route) {
            FeedScreen()
        }
    }
}
