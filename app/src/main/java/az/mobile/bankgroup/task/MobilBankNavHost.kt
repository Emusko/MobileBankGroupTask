package az.mobile.bankgroup.task

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import az.mobile.bankgroup.task.presentation.screens.feed.FeedScreen
import az.mobile.bankgroup.task.presentation.screens.feed.FeedViewModel
import az.mobile.bankgroup.task.presentation.screens.symbolDetails.SymbolDetailsScreen
import az.mobile.bankgroup.task.presentation.screens.symbolDetails.SymbolDetailsViewModel

@Composable
fun MobilBankNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MobilBankScreen.FEED.route,
        modifier = modifier,
    ) {
        composable(MobilBankScreen.FEED.route) {
            val feedViewModel: FeedViewModel = hiltViewModel()
            FeedScreen(
                viewModel = feedViewModel,
                onSymbolClick = { symbol ->
                    navController.navigate(MobilBankScreen.symbolDetailsRoute(symbol))
                },
            )
        }
        composable(
            route = MobilBankScreen.SYMBOL_DETAILS.route,
            arguments = listOf(
                navArgument(MobilBankScreen.SYMBOL_ARG) {
                    type = NavType.StringType
                },
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = MobilBankScreen.SYMBOL_DETAILS_DEEP_LINK },
            ),
        ) {
            val symbolDetailsViewModel: SymbolDetailsViewModel = hiltViewModel()
            SymbolDetailsScreen(viewModel = symbolDetailsViewModel)
        }
    }
}
